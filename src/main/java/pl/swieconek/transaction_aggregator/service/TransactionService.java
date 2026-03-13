package pl.swieconek.transaction_aggregator.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import pl.swieconek.transaction_aggregator.dto.TransactionDto;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service
public class TransactionService {
    private final RestClient restClient;

    public TransactionService() {
        restClient = RestClient.create();
    }

    @Cacheable("transactions")
    public List<TransactionDto> getTransactions(String account) {
        CompletableFuture<TransactionDto[]> response1 = CompletableFuture.supplyAsync(() -> fetchFromServer("http://localhost:8888/transactions?account=" + account));
        CompletableFuture<TransactionDto[]> response2 = CompletableFuture.supplyAsync(() -> fetchFromServer("http://localhost:8889/transactions?account=" + account));

        TransactionDto[] response1List = response1.join();
        TransactionDto[] response2List = response2.join();

        return Stream.concat(Arrays.stream(response1List), Arrays.stream(response2List))
                .sorted(Comparator.comparing(TransactionDto::timestamp).reversed())
                .toList();
    }

    private TransactionDto[] fetchFromServer(String url) {
        for (int i = 0; i < 5; i++) {
            try {
                return restClient.get()
                        .uri(url)
                        .retrieve()
                        .body(TransactionDto[].class);
            } catch (RestClientResponseException e) {
                int code = e.getStatusCode().value();

                if (code == 503 || code == 529) {
                    if (i == 4) {
                        throw e;
                    }
                } else {
                    throw e;
                }
            }
        }
        throw new IllegalStateException();
    }
}

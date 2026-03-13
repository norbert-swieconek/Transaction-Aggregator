package pl.swieconek.transaction_aggregator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.swieconek.transaction_aggregator.dto.TransactionDto;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class TransactionService {
    private final RestClient restClient;

    public TransactionService() {
        restClient = RestClient.create();
    }

    public List<TransactionDto> getTransactions(String account) {
        TransactionDto[] response1 = restClient.get()
                .uri("http://localhost:8888/transactions?account=" + account)
                .retrieve()
                .body(TransactionDto[].class);

        TransactionDto[] response2 = restClient.get()
                .uri("http://localhost:8889/transactions?account=" + account)
                .retrieve()
                .body(TransactionDto[].class);

        return Stream.concat(Arrays.stream(response1), Arrays.stream(response2))
                .sorted(Comparator.comparing(TransactionDto::timestamp).reversed())
                .toList();
    }
}

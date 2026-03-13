package pl.swieconek.transaction_aggregator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("aggregate")
public class AggregationController {
    private final RestClient restClient;

    public AggregationController() {
        restClient = RestClient.create();
    }

    @GetMapping()
    public String ping() {
        return restClient.get()
                .uri("http://localhost:8889/ping")
                .retrieve()
                .body(String.class);
    }
}

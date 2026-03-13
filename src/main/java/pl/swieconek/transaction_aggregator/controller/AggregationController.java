package pl.swieconek.transaction_aggregator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.swieconek.transaction_aggregator.dto.TransactionDto;
import pl.swieconek.transaction_aggregator.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("aggregate")
public class AggregationController {
    private final TransactionService transactionService;

    public AggregationController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping()
    public List<TransactionDto> getAggregatedTransactions(@RequestParam String account) {
        return transactionService.getTransactions(account);
    }
}

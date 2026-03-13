package pl.swieconek.transaction_aggregator.dto;

public record TransactionDto(String id, String serverId, String account, String amount, String timestamp) {

}

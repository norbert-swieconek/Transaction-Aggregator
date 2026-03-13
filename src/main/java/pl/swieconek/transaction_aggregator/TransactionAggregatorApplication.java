package pl.swieconek.transaction_aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TransactionAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionAggregatorApplication.class, args);
	}

}

package pe.com.bank.account.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import pe.com.bank.account.dto.TransactionDTO;
import pe.com.bank.account.entity.Account;
import pe.com.bank.account.entity.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class TransactionsRestClient {
	
	
	private WebClient webClient;

	@Value("http://localhost:8087/v1/transactions")
	private String TransactionURL;
	
	public TransactionsRestClient(WebClient webClient) {
		this.webClient = webClient;
	}
	
	// GET
    public Flux<Transaction> retrieveTransaction(String accountNumber){

        var url = TransactionURL.concat("/account/{id}");
        return webClient
                .get()
                .uri(url, accountNumber)
                .retrieve()
                .bodyToFlux(Transaction.class);
    }
    
    // POST
    public Mono<TransactionDTO> createTransactionUpdate(TransactionDTO transaction){
        var url = TransactionURL.concat("/amountUpdate");
        return webClient.post()
                .uri(url)
                .body(Mono.just(transaction), TransactionDTO.class)
                .retrieve()
                .bodyToMono(TransactionDTO.class);
    }
	
}

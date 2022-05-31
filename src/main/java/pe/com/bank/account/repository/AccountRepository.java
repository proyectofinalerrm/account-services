package pe.com.bank.account.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.com.bank.account.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account,String> {
	
	// Accounts (byAccountNumber)
	Mono<Account> findAccountsByAccountNumber(String accountNumber);
	
}

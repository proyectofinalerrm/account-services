package pe.com.bank.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.com.bank.account.dto.AccountTransactionDTO;
import pe.com.bank.account.entity.Account;
import pe.com.bank.account.entity.Transaction;
import pe.com.bank.account.repository.AccountRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public interface AccountService {
	
	public Flux<Account> getAccounts();
	
	public Mono<Account> getAccountById(String id);
	
	public Mono<Account> newAccount(Account account);
	
	public Mono<Void> deleteAccountById(String id);
	
	public Mono<Account> updateAccount(Account account, String id);
	
	// Accounts (byAccountNumber)
	public Mono<Account> getAccountByAccountNum(String accountNumber);
	

}

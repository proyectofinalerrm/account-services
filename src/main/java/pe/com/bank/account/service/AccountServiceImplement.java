package pe.com.bank.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pe.com.bank.account.client.TransactionsRestClient;
import pe.com.bank.account.dto.AccountTransactionDTO;
import pe.com.bank.account.entity.Account;
import pe.com.bank.account.entity.Transaction;
import pe.com.bank.account.repository.AccountRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImplement implements AccountService{
	
	@Autowired
	AccountRepository accountRepository;
	
	TransactionsRestClient transactionRestClient;

	//INI CRUD - Account
	@Override
	public Flux<Account> getAccounts() {
		
		return accountRepository.findAll();
	}

	@Override
	public Mono<Account> getAccountById(String id) {
		
		return accountRepository.findById(id);
	}

	@Override
	public Mono<Account> newAccount(Account account) {
		
		return accountRepository.save(account);
	}

	@Override
	public Mono<Void> deleteAccountById(String id) {
		
		return accountRepository.deleteById(id);
	}

	@Override
	public Mono<Account> updateAccount(Account account, String id) {
		
		return accountRepository.findById(id).flatMap(a -> {
					a.setAccountNumber(account.getAccountNumber());
					a.setAmount(account.getAmount());
					a.setAmounttype(account.getAmounttype());
					a.setDateOpen(account.getDateOpen());
					return accountRepository.save(a);
				});
	}
	
	// FIN CRUD - Account
	
	
	// Accounts (byAccountNumber)

	@Override
	public Mono<Account> getAccountByAccountNum(String accountNumber) {
		
		return accountRepository.findAccountsByAccountNumber(accountNumber);
	}






}

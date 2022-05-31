package pe.com.bank.account.controller;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pe.com.bank.account.client.TransactionsRestClient;
import pe.com.bank.account.dto.AccountTransactionDTO;
import pe.com.bank.account.dto.TransactionDTO;
import pe.com.bank.account.entity.Account;
import pe.com.bank.account.entity.MovementEntity;
import pe.com.bank.account.entity.Transaction;
import pe.com.bank.account.service.AccountService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/v1") 
public class AccountController {

	AccountService accountService;
	TransactionsRestClient transactionRestClient;
	
	//INI CRUD - Account
	@GetMapping("/accounts")
	public Mono<ResponseEntity<Flux<Account>>> getAccountsList(){		//Listar Cuentas
		
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(accountService.getAccounts()));
	}
	
	@GetMapping("/accounts/{id}")
	public Mono<ResponseEntity<Account>> getAccountById(@PathVariable String id){	//Listar Cuenta por Id
		
		return accountService.getAccountById(id).map(p -> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(p)).defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/accounts")
	public Mono<Account> addNewAccount(@RequestBody Account account){	//Agregar nueva cuenta	
		
		return accountService.newAccount(account);
	}
	
	
	@DeleteMapping("/accounts/{id}")
	public Mono<Void> deleteAccountById(@PathVariable String id){	//Eliminar cuenta por Id
		
		return accountService.deleteAccountById(id);
	}

	@PutMapping("/accounts/{id}")
	public Mono<ResponseEntity<Account>> updateAccount(@RequestBody Account account, @PathVariable String id){
		
		return accountService.updateAccount(account, id)
				.map(ResponseEntity.ok()::body)
				.defaultIfEmpty(ResponseEntity.notFound().build())
				.log();
	}
	
	//FIN CRUD - Account
	
	
	// Accounts (byAccountNumber)
    
	@GetMapping("/accountsNumber/{accountNumber}")
	public Mono<Account> getAccountsByAccountNumberX(@PathVariable("accountNumber") String accountNumber){
		return accountService.getAccountByAccountNum(accountNumber);
	}
	
	
	// Account-Transaction (by accountId)
	
	@GetMapping("/accountTransactions/{id}")
	public Mono<AccountTransactionDTO> retrieveAccountAndTransactionsByAccountId(@PathVariable("id") String accountId) { 
		
		return accountService.getAccountById(accountId).flatMap(account -> {
					return transactionRestClient.retrieveTransaction(account.getAccountNumber()).collectList().map(a -> 
						new AccountTransactionDTO(
									account.getAccount_id(),
									account.getAccountNumber(),
									account.getAmount(),
									account.getDateOpen(),
									account.getAmounttype(),
									a
									));
				});
	}
	
	// Actualizar ammount : Retiro 

	@PostMapping("/updateAmountRest/{id}")
	public Mono<TransactionDTO> updateRestAmountByAccountId(@RequestBody MovementEntity movEntity){
		
			return accountService.getAccountById(movEntity.getAccount_id()).flatMap(crc -> {
				var r = accountService.updateAccount(new Account(	crc.getAccount_id(), 
																	crc.getAccountNumber(), 
																	crc.getAmount() - movEntity.getAmount(),
																	crc.getDateOpen(), 
																	crc.getAmounttype(), 
																	crc.getCustomerId()), movEntity.getAccount_id());	

					return r.flatMap( dsf -> {			
						var r2 = transactionRestClient.createTransactionUpdate(new TransactionDTO(	movEntity.getAmount(),
																									movEntity.getDate(),
																									movEntity.getType(),
																									movEntity.getAccount_id())); 
								
							return r2.map( sd -> new TransactionDTO(movEntity.getAmount(),
																	movEntity.getDate(),
																	movEntity.getType(),
																	movEntity.getAccount_id()));
										
					
							});
		
		});
		
	} 
	
	// Actualizar ammount : Deposito 
	
	@PostMapping("/updateAmountSum/{id}")
	public Mono<TransactionDTO> updateSumAmountByAccountId(@RequestBody MovementEntity movEntity){
		
			return accountService.getAccountById(movEntity.getAccount_id()).flatMap(crc -> {
				var r = accountService.updateAccount(new Account(	crc.getAccount_id(), 
																	crc.getAccountNumber(), 
																	crc.getAmount() + movEntity.getAmount(),
																	crc.getDateOpen(), 
																	crc.getAmounttype(), 
																	crc.getCustomerId()), movEntity.getAccount_id());	

					return r.flatMap( dsf -> {			
						var r2 = transactionRestClient.createTransactionUpdate(new TransactionDTO(	movEntity.getAmount(),
																									movEntity.getDate(),
																									movEntity.getType(),
																									movEntity.getAccount_id())); 
								
							return r2.map( sd -> new TransactionDTO(movEntity.getAmount(),
																	movEntity.getDate(),
																	movEntity.getType(),
																	movEntity.getAccount_id()));
										
					
							});
		
		});
		
	} 
	
	
	
	// Account-Transaction (by accountNumber)
	/*
	@GetMapping("/accountTransactionsByAccNum/{accountNumber}")
	public Mono<AccountTransactionDTO> retrieveAccountAndTransactionsByAccountNumber(@PathVariable("accountNumber") String accountNumber) { 
		//return accountRepository.findById(id).flatMap(account -> {
		
		return accountService.getAccountByAccountNum(accountNumber).flatMap(account -> {
					//return transactionRestClient.retrieveTransaction(account.getAccountNumber()).collectList().map(a -> 
					return transactionRestClient.retrieveTransaction(account.getAccountNumber()).collectList().map(a -> 
						new AccountTransactionDTO(
									account.getAccount_id(),
									account.getAccountNumber(),
									account.getAmount(),
									account.getDateOpen(),
									account.getAmounttype(),
									a
									));
				});
	}
	*/
	
}

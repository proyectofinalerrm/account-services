package pe.com.bank.account.entity;

import java.sql.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import pe.com.bank.project1.entity.Transaction;

@Document(collection="account")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {

	@Id
	private String account_id;
	private String accountNumber;
	private double amount;
	private String dateOpen;
	private String amounttype;
	
	private String customerId;
	
	//private String transactionId;
	
	//private List<String> TransacctionId;
	//private List<String> AccountNumber;
	//private Product product;
	//private Customer customer;
	
}

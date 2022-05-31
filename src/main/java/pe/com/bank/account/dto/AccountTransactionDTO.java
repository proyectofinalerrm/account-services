package pe.com.bank.account.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.bank.account.entity.Transaction;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransactionDTO {

	private String account_id;
	private String accountNumber;
	private double amount;
	private String dateOpen;
	private String amounttype;
	
	private List<Transaction> transactionList;
}

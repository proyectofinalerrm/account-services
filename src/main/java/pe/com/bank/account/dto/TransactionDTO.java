package pe.com.bank.account.dto;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.bank.account.entity.Transaction;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

	//private String transactionId;
	private double amount;
	private String date;
	private String type;
	private String accountNumber;
}

package pe.com.bank.account.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="transaction")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {

	@Id
	private String transactionId;
	private double amount;
	private String date;
	private String type;
	private String accountNumber;
	private String creditId;
	
	//private List<String> TransacctionId;
}

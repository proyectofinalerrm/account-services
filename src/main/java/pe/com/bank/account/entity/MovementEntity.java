package pe.com.bank.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementEntity {

	private String account_id;
	private Double amount;
	private String type;
	private String date;
	
}

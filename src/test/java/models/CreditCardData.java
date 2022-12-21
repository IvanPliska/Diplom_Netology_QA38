package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardData {
    private String id;
    private String bank_id;
    private String created;
    private String status;
}
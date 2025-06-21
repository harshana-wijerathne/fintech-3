package site.wijerathne.harshana.fintech.model;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Transaction {
    private String transactionId;
    private String accountNumber;
    private BigDecimal amount;
    private BigDecimal balance;
    private String transactionType;
    private String description;
    private String referenceNumber;
    private Timestamp createdAt;


}


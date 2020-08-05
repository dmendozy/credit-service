package com.credit.credit.adds;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Transaction {
    public String transactionId;
    public String transactionName;
    public double amount;
    public double commission;
    public LocalDateTime dateTime;
    public String accountId;
    public String creditId;

    public Transaction(String transactionName, double amount, LocalDateTime dateTime, String creditId) {
        this.transactionName = transactionName;
        this.amount = amount;
        this.dateTime = dateTime;
        this.creditId = creditId;
    }
}

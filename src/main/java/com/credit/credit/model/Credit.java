package com.credit.credit.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
@Document(collection = "credits")
public class Credit {
    @Id
    public String creditId;
    public String bankId;
    public String numberCredit;
    public String typeAccount;
    public double creditLimit;
    public LocalDate creationDate;
    public String customerId;
    @Transient
    public List<String> transactions;

}

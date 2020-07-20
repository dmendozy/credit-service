package com.credit.credit.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
@Document(collection = "credits")
public class Credit {
    @Id
    public String creditId;
    public String numberCredit;
    public String typeAccount;
    public double crediLimit;

}

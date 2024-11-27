package org.poo.associated.userRelated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @AllArgsConstructor
public class Account {
    private String IBAN;
    private double balance;
    private String currency;
    private String type;
    private List<Card> cards = new ArrayList<>();
}

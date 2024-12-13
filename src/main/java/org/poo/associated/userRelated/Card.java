package org.poo.associated.userRelated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class Card {
    private String cardNumber;
    private String status;
    @JsonIgnore
    private boolean oneTimeCard;

    public Card(String cardNumber, boolean isOneTimeCard) {
        this.cardNumber = cardNumber;
        this.status = "active";
        this.oneTimeCard = isOneTimeCard;
    }
}

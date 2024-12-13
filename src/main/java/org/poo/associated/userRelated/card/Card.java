package org.poo.associated.userRelated.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public final class Card {
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

package org.poo.associated.userRelated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.poo.utils.Utils;

@Getter @AllArgsConstructor
public class Card {
    private String cardNumber;
    private String status;
    @JsonIgnore
    private boolean oneTimeCard;

    public Card(boolean isOneTimeCard) {
        this.cardNumber = Utils.generateCardNumber();
        this.status = "active";
        this.oneTimeCard = isOneTimeCard;
    }
}

package org.poo.associated.userRelated.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * Clasa care reprezinta un card. Pentru simplitate am ales
 * sa folosesc o singura clasa pentru cele doua tipuri de
 * carduri valabile.
 */
@Getter @Setter
public final class Card {
    private String cardNumber;
    private String status;
    @JsonIgnore
    private boolean oneTimeCard;

    public Card(final String cardNumber, final boolean isOneTimeCard) {
        this.cardNumber = cardNumber;
        this.status = "active";
        this.oneTimeCard = isOneTimeCard;
    }
}

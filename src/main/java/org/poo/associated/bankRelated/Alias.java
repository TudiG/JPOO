package org.poo.associated.bankRelated;

import lombok.AllArgsConstructor;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;

/**
 * Clasa aceasta reprezinta alias-urile custom ale unui
 * user.
 */
@AllArgsConstructor
public final class Alias {
    private String email;
    private Account account;
    private String alias;
}

package org.poo.associated.bankRelated;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;

@AllArgsConstructor @Data
public class Alias {
    private String email;
    private Account account;
    private String alias;
    private int timestamp;
}

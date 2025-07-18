package org.poo.associated.userRelated.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.UserInput;
import java.util.ArrayList;
import java.util.List;

/**
 * Aceasta clasa centralizeaza toate datele unui utilizator,
 * reprezantand profilul unui utilizator al bancii.
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public final class User {
    private String firstName;
    private String lastName;
    private String email;
    private List<Account> accounts;

    public User(final UserInput userInput) {
        firstName = userInput.getFirstName();
        lastName = userInput.getLastName();
        email = userInput.getEmail();
        accounts = new ArrayList<>();
    }
}

package org.poo.associated.userRelated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.UserInput;
import java.util.ArrayList;
import java.util.List;

@Getter @AllArgsConstructor @NoArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private List<Account> accounts;

    public User(UserInput userInput) {
        firstName = userInput.getFirstName();
        lastName = userInput.getLastName();
        email = userInput.getEmail();
        accounts = new ArrayList<>();
    }
}

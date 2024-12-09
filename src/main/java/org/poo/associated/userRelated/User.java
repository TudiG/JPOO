package org.poo.associated.userRelated;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<Transaction> transactions;
    @JsonIgnore
    private List<String> aliases;

    public User(UserInput userInput) {
        firstName = userInput.getFirstName();
        lastName = userInput.getLastName();
        email = userInput.getEmail();
        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
        aliases = new ArrayList<>();
    }
}

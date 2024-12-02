package org.poo.associated.userRelated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
        this.firstName = userInput.getFirstName();
        this.lastName = userInput.getLastName();
        this.email = userInput.getEmail();
        this.accounts = new ArrayList<>();
    }
}

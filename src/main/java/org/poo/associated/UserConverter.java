package org.poo.associated;

import org.poo.associated.userRelated.User;
import org.poo.fileio.UserInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
    public static List<User> convertUserFromInput(final UserInput[] userInputs) {
        return Arrays.stream(userInputs).map(User::new).collect(Collectors.toList());
    }
}

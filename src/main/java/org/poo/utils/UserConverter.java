package org.poo.utils;

import org.poo.associated.userRelated.User;
import org.poo.fileio.UserInput;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class UserConverter {
    public static List<User> convertUsersFromInput(final UserInput[] userInputs) {
        return Arrays.stream(userInputs).map(User::new).collect(Collectors.toList());
    }
}

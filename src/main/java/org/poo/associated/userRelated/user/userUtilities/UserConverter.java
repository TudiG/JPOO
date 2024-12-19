package org.poo.associated.userRelated.user.userUtilities;

import org.poo.associated.userRelated.user.User;
import org.poo.fileio.UserInput;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class UserConverter {
    private UserConverter() { }

    /**
     *
     * @param userInputs
     * @return
     */
    public static List<User> convertUsersFromInput(final UserInput[] userInputs) {
        return Arrays.stream(userInputs).map(User::new).collect(Collectors.toList());
    }
}

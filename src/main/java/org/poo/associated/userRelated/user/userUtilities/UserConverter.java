package org.poo.associated.userRelated.user.userUtilities;

import org.poo.associated.userRelated.user.User;
import org.poo.fileio.UserInput;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class UserConverter {
    private UserConverter() { }

    /**
     * Metoda aceasta converteste un vector de userInputs intr-o
     * lista de obiecete de tip User pentru prelucrarea eventuala.
     *
     * @param userInputs vectorul de userInputs dat la intrare
     * @return o lista cu obiecte de tip User
     */
    public static List<User> convertUsersFromInput(final UserInput[] userInputs) {
        return Arrays.stream(userInputs).map(User::new).collect(Collectors.toList());
    }
}

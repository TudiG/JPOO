package org.poo.associated.bank;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.CommandService;
import org.poo.associated.UserConverter;
import org.poo.associated.userRelated.User;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ObjectInput;
import org.poo.fileio.UserInput;

import java.util.List;

public class BankInitializer {
    public void initialize(final ObjectInput inputData, final ArrayNode output) {
        CommandInput[] commandInputs = inputData.getCommands();

        UserInput[] userInputs = inputData.getUsers();

        List<User> users = UserConverter.convertUserFromInput(userInputs);

        Bank bank = new Bank(users);

        CommandService commandService = new CommandService();

        for (CommandInput commandInput : commandInputs) {
            commandService.executeCommands(commandInput, bank, output);
        }
    }
}

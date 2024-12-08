package org.poo.associated.bankRelated;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.commandService.CommandService;
import org.poo.utils.SimpleRateMapConverter;
import org.poo.utils.UserConverter;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ObjectInput;

import java.util.Arrays;

public class BankInitializer {
    public void initialize(final ObjectInput inputData, final ArrayNode output) {
        CommandInput[] commandInputs = inputData.getCommands();

        Bank.getInstance().addAllUsers(UserConverter.convertUsersFromInput(inputData.getUsers()));

        SimpleRateMapConverter.precomputeRates(Arrays.asList(inputData.getExchangeRates()));

        for (CommandInput commandInput : commandInputs) {
            CommandService.getInstance().executeCommands(commandInput, Bank.getInstance(), output);
        }
    }
}

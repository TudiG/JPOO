package org.poo.associated.bankRelated;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.commandService.CommandService;
import org.poo.utils.SimpleRateMapConverter;
import org.poo.associated.userRelated.user.userUtilities.UserConverter;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ObjectInput;
import org.poo.utils.Utils;

import java.util.Arrays;

public final class BankInitializer {
    private static BankInitializer instance;

    private BankInitializer() {

    }

    /**
     *
     * @return
     */
    public static synchronized BankInitializer getInstance() {
        if (instance == null) {
            instance = new BankInitializer();
        }

        return instance;
    }

    /**
     *
     * @param inputData
     * @param output
     */
    public void initialize(final ObjectInput inputData, final ArrayNode output) {
        clearAllData();

        Bank.getInstance().addAllUsers(UserConverter.convertUsersFromInput(inputData.getUsers()));

        SimpleRateMapConverter.precomputeRates(Arrays.asList(inputData.getExchangeRates()));

        for (CommandInput commandInput : inputData.getCommands()) {
            CommandService.getInstance().executeCommands(commandInput, output);
        }
    }

    /**
     *
     */
    private void clearAllData() {
        Utils.resetRandom();

        SimpleRateMapConverter.getRatesMap().clear();

        Bank bank = Bank.getInstance();
        bank.getUserTransactionsDatabase().clear();
        bank.getAliasDatabase().clear();
    }
}

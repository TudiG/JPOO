package org.poo.associated.bankRelated;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.commandService.CommandService;
import org.poo.utils.SimpleRateMapConverter;
import org.poo.associated.userRelated.user.userUtilities.UserConverter;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ObjectInput;
import org.poo.utils.Utils;

import java.util.Arrays;

/**
 * Aceasta clasa initializeaza, trece secvential prin fiecare comanda,
 * apoi curata toate datele aferente ale bancii intre teste.
 */
public final class BankInitializer {
    private static BankInitializer instance;

    private BankInitializer() {

    }

    /**
     * Getter-ul pentru instanta BankInitializer.
     * @return instanta Singleton a clasei.
     */
    public static synchronized BankInitializer getInstance() {
        if (instance == null) {
            instance = new BankInitializer();
        }

        return instance;
    }

    /**
     * Metoda aceasta initializeaza toate datele necesare rularii
     * programului, urmand sa le curete pentru urmatoarea rulare.
     * @param inputData datele de la intrarea, specifice pentru fiecare test
     * @param output ArrayNode-ul pentru serializarea output-ului
     */
    public void initialize(final ObjectInput inputData, final ArrayNode output) {
        Bank.getInstance().addAllUsers(UserConverter.convertUsersFromInput(inputData.getUsers()));
        SimpleRateMapConverter.precomputeRates(Arrays.asList(inputData.getExchangeRates()));

        for (CommandInput commandInput : inputData.getCommands()) {
            CommandService.getInstance().executeCommands(commandInput, output);
        }

        clearAllData();
    }

    /**
     * Metoda care asigura curatarea tuturor datelor unui test deja
     * efectuat.
     */
    private void clearAllData() {
        Utils.resetRandom();

        SimpleRateMapConverter.getRatesMap().clear();

        Bank bank = Bank.getInstance();
        bank.getUserTransactionsDatabase().clear();
        bank.getAliasDatabase().clear();
    }
}

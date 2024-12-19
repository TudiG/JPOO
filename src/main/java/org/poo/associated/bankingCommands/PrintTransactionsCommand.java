package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandUtilities.BankingCommand;
import org.poo.associated.bankingCommands.commandUtilities.StaticOutputs;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;
import java.util.List;

/**
 * Comanda care afiseaza toate tranzactiile unui utilizator specificat.
 */
public final class PrintTransactionsCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        List<Transaction> transactionArray = Bank.getInstance()
                .getUserTransactionsDatabase().get(commandInput.getEmail());

        StaticOutputs.printArrayToOutput(commandInput, output, transactionArray);
    }


}


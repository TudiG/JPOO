package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandUtilities.BankingCommand;
import org.poo.associated.bankingCommands.commandUtilities.StaticOutputs;
import org.poo.fileio.CommandInput;

/**
 * Comanda care afiseaza toti utilizatorii bancii.
 */
public final class PrintUsersCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        StaticOutputs.printArrayToOutput(commandInput, output, Bank.getInstance().getUsers());
    }
}

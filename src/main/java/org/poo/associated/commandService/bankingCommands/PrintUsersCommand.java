package org.poo.associated.commandService.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.commandService.commandUtilities.BankingCommand;
import org.poo.associated.commandService.commandUtilities.StaticOutputs;
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

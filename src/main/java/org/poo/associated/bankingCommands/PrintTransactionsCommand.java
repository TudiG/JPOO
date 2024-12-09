package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.fileio.CommandInput;

public class PrintTransactionsCommand implements BankingCommand {
    @Override
    public void execute(CommandInput commandInput, ArrayNode output) {

    }
}

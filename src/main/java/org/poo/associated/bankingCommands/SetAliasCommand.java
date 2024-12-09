package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.fileio.CommandInput;

public class SetAliasCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {

    }
}

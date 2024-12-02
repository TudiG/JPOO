package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.CommandInput;

public interface BankingCommand {
    public void execute(final CommandInput commandInput,  final ArrayNode output);
}

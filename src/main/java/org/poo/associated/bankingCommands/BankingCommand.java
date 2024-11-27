package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;

public interface BankingCommand {
    public void execute(final ArrayNode output);
}

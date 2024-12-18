package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.transaction.Transaction;
import org.poo.fileio.CommandInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PrintTransactionsCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();

        Bank bank = Bank.getInstance();

        List<Transaction> transactionArray = bank.getUserTransactionsDatabase().get(commandInput.getEmail());

        fieldNode.put("command", commandInput.getCommand());
        fieldNode.set("output", mapper.valueToTree(transactionArray));
        fieldNode.put("timestamp", commandInput.getTimestamp());

        output.add(fieldNode);
    }
}


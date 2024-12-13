package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.fileio.CommandInput;

public class PrintTransactionsCommand implements BankingCommand {
    @Override
    public void execute(CommandInput commandInput, ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();

        ArrayNode transactions = Bank.getInstance().getTransactionDatabase().get(commandInput.getEmail());
        if (transactions == null) {
            transactions = mapper.createArrayNode();
        }

        ArrayNode filteredTransactions = mapper.createArrayNode();
        transactions.forEach(transaction -> {
            if (transaction.has("timestamp") && transaction.get("timestamp").asInt() <= commandInput.getTimestamp()) {
                filteredTransactions.add(transaction);
            }
        });

        fieldNode.put("command", commandInput.getCommand());
        fieldNode.set("output", filteredTransactions);
        fieldNode.put("timestamp", commandInput.getTimestamp());

        output.add(fieldNode);
    }
}


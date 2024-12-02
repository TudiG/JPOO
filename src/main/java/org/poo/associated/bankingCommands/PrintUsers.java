package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bank.Bank;
import org.poo.fileio.CommandInput;

public class PrintUsers implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();

        fieldNode.put("command", "printUsers");
        fieldNode.put("output", mapper.valueToTree(Bank.getInstance().getUsers()));
        fieldNode.put("timestamp", commandInput.getTimestamp());

        output.add(fieldNode);
    }
}

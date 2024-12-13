package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.fileio.CommandInput;

public class DeleteAccountCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank.getInstance().getUsers().stream()
                .filter(user -> commandInput.getEmail().equals(user.getEmail()))
                .findFirst()
                .ifPresentOrElse(user -> {
                    // TODO 
                    // removed se va folosi in caz ca nu exista contul, dat exista user-ul
                    boolean removed = user.getAccounts().removeIf(account -> 
                        commandInput.getAccount().equals(account.getIBAN()) && account.getBalance() == 0
                    );

                    ObjectMapper mapper = new ObjectMapper();

                    ObjectNode fieldNode = mapper.createObjectNode();
                    fieldNode.put("command", commandInput.getCommand());

                    ObjectNode outputNode = mapper.createObjectNode();

                    String operationResult =  removed ? "success" : "error";
                    String operationMessage = removed ? "Account deleted" : "Account couldn't be deleted - see org.poo.transactions for details";

                    outputNode.put(operationResult, operationMessage);
                    outputNode.put("timestamp", commandInput.getTimestamp());

                    fieldNode.set("output", outputNode);
                    fieldNode.put("timestamp", commandInput.getTimestamp());

                    output.add(fieldNode);
                }, () -> {
                    // error
                });
    }
}

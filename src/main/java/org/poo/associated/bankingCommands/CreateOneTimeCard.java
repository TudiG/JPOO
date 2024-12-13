package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.card.Card;
import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

public class CreateOneTimeCard implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        String cardNumber = Utils.generateCardNumber();

        Bank.getInstance().getUsers().stream()
                .filter(user -> commandInput.getEmail().equalsIgnoreCase(user.getEmail()))
                .findAny()
                .ifPresent(selectedUser -> selectedUser.getAccounts().stream()
                        .filter(account -> commandInput.getAccount().equals(account.getIBAN()))
                        .forEach(account -> account.getCards().add(new Card(cardNumber, true))));

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();

        fieldNode.put("account", commandInput.getAccount());
        fieldNode.put("card", cardNumber);
        fieldNode.put("cardHolder", commandInput.getEmail());
        fieldNode.put("description", "New card created");
        fieldNode.put("timestamp", commandInput.getTimestamp());

        ArrayNode transactionArray = Bank.getInstance().getTransactionDatabase().get(commandInput.getEmail());
        if(transactionArray != null) {
            transactionArray.add(fieldNode);
        }
    }
}

package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.CommandInput;

public class DeleteCardCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();

        Bank bank = Bank.getInstance();
        ArrayNode transactionArray = bank.getTransactionDatabase().get(commandInput.getEmail());
        Account accountToRemoveFrom = bank.findAccountByCardNumber(commandInput.getCardNumber());

        Bank.getInstance().getUsers().stream()
                .filter(user -> commandInput.getEmail().equalsIgnoreCase(user.getEmail()))
                .findAny()
                .ifPresent(selectedUser -> selectedUser.getAccounts()
                        .forEach(account -> account.getCards()
                                .removeIf(card -> commandInput.getCardNumber().equals(card.getCardNumber()))
                        )
                );

        if(accountToRemoveFrom != null) {
            fieldNode.put("account", accountToRemoveFrom.getIBAN());
            fieldNode.put("cardHolder", commandInput.getEmail());
            fieldNode.put("card", commandInput.getCardNumber());
            fieldNode.put("description", "The card has been destroyed");
            fieldNode.put("timestamp", commandInput.getTimestamp());

            transactionArray.add(fieldNode);
        }
    }
}

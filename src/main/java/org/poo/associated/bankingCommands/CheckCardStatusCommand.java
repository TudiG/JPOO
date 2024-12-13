package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.card.Card;
import org.poo.associated.userRelated.user.User;
import org.poo.fileio.CommandInput;

public class CheckCardStatusCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();
        ObjectNode outputNode = mapper.createObjectNode();

        Bank bank = Bank.getInstance();
        Card card = Bank.getInstance().findCardByNumber(commandInput.getCardNumber());

        if (card == null) {
            fieldNode.put("command", commandInput.getCommand());

            outputNode.put("description", "Card not found");
            outputNode.put("timestamp", commandInput.getTimestamp());

            fieldNode.set("output", outputNode);
            fieldNode.put("timestamp", commandInput.getTimestamp());

            output.add(fieldNode);
            return;
        }

        Account account = bank.findAccountByCardNumber(card.getCardNumber());
        User user = bank.findUserByCardNumber(commandInput.getCardNumber());
        ArrayNode transactionArray = bank.getTransactionDatabase().get(user.getEmail());

        if (account.getMinimumBalance() >= account.getBalance()) {
            card.setStatus("frozen");

            fieldNode.put("description", "You have reached the minimum amount of funds, the card will be frozen");
            fieldNode.put("timestamp", commandInput.getTimestamp());

            transactionArray.add(fieldNode);
        }
    }
}

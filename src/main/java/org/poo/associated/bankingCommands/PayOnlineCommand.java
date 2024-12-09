package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.CommandInput;
import org.poo.utils.SimpleRateMapConverter;

public class PayOnlineCommand implements BankingCommand {
    Bank bank = Bank.getInstance();

    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Account account = Bank.getInstance().findAccountByCardNumber(commandInput.getCardNumber());

        if (account != null) {
            String rateKey = commandInput.getCurrency() + "-" + account.getCurrency();
            double rate = SimpleRateMapConverter.ratesMap.get(rateKey);
            account.subtractFunds(rate * commandInput.getAmount());
        } else {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode fieldNode = mapper.createObjectNode();
            fieldNode.put("command", commandInput.getCommand());

            ObjectNode outputNode = mapper.createObjectNode();
            outputNode.put("description", "Card not found");
            outputNode.put("timestamp", commandInput.getTimestamp());

            fieldNode.set("output", outputNode);
            fieldNode.put("timestamp", commandInput.getTimestamp());

            output.add(fieldNode);
        }
    }
}

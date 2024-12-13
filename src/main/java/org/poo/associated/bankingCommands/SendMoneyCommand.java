package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.CommandInput;
import org.poo.utils.SimpleRateMapConverter;
import org.poo.utils.Utils;

// !TODO STRATEGY DOABLE

public final class SendMoneyCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();

        Bank bank = Bank.getInstance();
        ArrayNode transactionArray = bank.getTransactionDatabase().get(commandInput.getEmail());

        if (Utils.isValidIBAN(commandInput.getReceiver()) && Utils.isValidIBAN(commandInput.getAccount())) {
            Account sender = bank.findAccountByIBAN(commandInput.getAccount());
            Account receiver = bank.findAccountByIBAN(commandInput.getReceiver());

            if(sender == null || receiver == null) {
                return;
            }

            if(sender.getBalance() < commandInput.getAmount()) {
                fieldNode.put("description", "Insufficient funds");
                fieldNode.put("timestamp", commandInput.getTimestamp());
                transactionArray.add(fieldNode);
                return;
            }

            String rateKey = sender.getCurrency() + "-" + receiver.getCurrency();
            Double rate = SimpleRateMapConverter.ratesMap.get(rateKey);
            double convertedAmount = commandInput.getAmount() * rate;

            sender.subtractFunds(commandInput.getAmount());
            receiver.addFunds(convertedAmount);

            fieldNode.put("timestamp", commandInput.getTimestamp());
            fieldNode.put("description", commandInput.getDescription());
            fieldNode.put("senderIBAN", sender.getIBAN());
            fieldNode.put("receiverIBAN", receiver.getIBAN());
            fieldNode.put("amount", commandInput.getAmount() + " " + sender.getCurrency());
            fieldNode.put("transferType", "sent");

            transactionArray.add(fieldNode);
        }
    }
}

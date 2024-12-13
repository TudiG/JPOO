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

public class SendMoneyCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();

        Account receiver = null;
        Account sender = null;

        if (Utils.isValidIBAN(commandInput.getReceiver()) && Utils.isValidIBAN(commandInput.getAccount())) {

            // if(!Utils.isValidIBAN(commandInput.getAccount())) {
            //     if(bank.getAliasDatabase().get(commandInput.getAccount()) != null) {
            //         sender = bank.getAliasDatabase().get(commandInput.getAccount()).getAccount();
            //     }
            // } else {
                sender = bank.findAccountByIBAN(commandInput.getAccount());
            // }

            // if(!Utils.isValidIBAN(commandInput.getReceiver())) {
            //     if(bank.getAliasDatabase().get(commandInput.getReceiver()) != null) {
            //         receiver = bank.getAliasDatabase().get(commandInput.getReceiver()).getAccount();
            //     }
            // } else {
                receiver = bank.findAccountByIBAN(commandInput.getReceiver());
            // }

            if (checkConditions(commandInput, receiver, sender)) return;


            String rateKey = sender.getCurrency() + "-" + receiver.getCurrency();
            Double rate = SimpleRateMapConverter.ratesMap.get(rateKey);
            double convertedAmount = commandInput.getAmount() * rate;

            sender.subtractFunds(commandInput.getAmount());
            receiver.addFunds(convertedAmount);
        }

        if (Utils.isValidIBAN(commandInput.getAccount()) && Utils.isValidIBAN(commandInput.getReceiver())) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode fieldNode = mapper.createObjectNode();

            fieldNode.put("timestamp", commandInput.getTimestamp());
            fieldNode.put("description", commandInput.getDescription());
            fieldNode.put("senderIBAN", sender.getIBAN());
            fieldNode.put("receiverIBAN", receiver.getIBAN());
            fieldNode.put("amount", commandInput.getAmount() + " " + sender.getCurrency());
            fieldNode.put("transferType", "sent");

            Bank.getInstance().getTransactionDatabase().get(commandInput.getEmail()).add(fieldNode);
        }
    }

    private static boolean checkConditions(CommandInput commandInput, Account receiver, Account sender) {
        // TODO
        // if(receiver == sender) {
        //     return true;
        // }

        if(sender == null || receiver == null) {
            return true;
        }

        if(sender.getBalance() < commandInput.getAmount()) {
            return true;
        }
        return false;
    }
}

package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.card.Card;
import org.poo.associated.userRelated.transaction.CardFrozenError;
import org.poo.associated.userRelated.commerciantReport.CommerciantReport;
import org.poo.associated.userRelated.transaction.InsufficientFundsError;
import org.poo.associated.userRelated.transaction.PayOnlineTransaction;
import org.poo.associated.userRelated.transaction.Transaction;
import org.poo.fileio.CommandInput;
import org.poo.utils.SimpleRateMapConverter;
import org.poo.utils.Utils;

import java.util.List;

public final class PayOnlineCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();
        Account account = bank.findAccountByCardNumber(commandInput.getCardNumber());

        List<Transaction> transactionsArray = bank.getUserTransactionsDatabase().get(commandInput.getEmail());
        Transaction transaction = null;

        if (account != null) {
            String rateKey = commandInput.getCurrency() + "-" + account.getCurrency();
            double rate = SimpleRateMapConverter.ratesMap.get(rateKey);
            double convertedAmount = rate * commandInput.getAmount();

            if(bank.findCardByNumber(commandInput.getCardNumber()).getStatus().equals("frozen")) {
                transaction = new CardFrozenError(commandInput.getTimestamp());
                transactionsArray.add(transaction);
                account.getAccountTransactions().add(transaction);
                return;
            }

            if(account.getBalance() < convertedAmount) {
                transaction = new InsufficientFundsError(commandInput.getTimestamp());
                transactionsArray.add(transaction);
                account.getAccountTransactions().add(transaction);
                return;
            }

            account.subtractFunds(convertedAmount);

            transaction = new PayOnlineTransaction(commandInput.getTimestamp(),
                    convertedAmount, commandInput.getCommerciant());

            CommerciantReport commerciant = new CommerciantReport(commandInput.getTimestamp(),
                    convertedAmount, commandInput.getCommerciant(), transaction);

            transactionsArray.add(transaction);
            account.getAccountTransactions().add(transaction);
            account.getCommerciantInteractions().add(commerciant);

            Card card = bank.findCardByNumber(commandInput.getCardNumber());
            if(card.isOneTimeCard()) {
                card.setCardNumber(Utils.generateCardNumber());
            }
        } else {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode fieldNode = mapper.createObjectNode();
            ObjectNode outputNode = mapper.createObjectNode();

            fieldNode.put("command", commandInput.getCommand());

            outputNode.put("description", "Card not found");
            outputNode.put("timestamp", commandInput.getTimestamp());

            fieldNode.set("output", outputNode);
            fieldNode.put("timestamp", commandInput.getTimestamp());

            output.add(fieldNode);
        }
    }
}

package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.transactions.InsufficientFundsError;
import org.poo.associated.userRelated.transactions.SendMoneyTransaction;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;
import org.poo.utils.SimpleRateMapConverter;
import org.poo.utils.Utils;
import java.util.List;

public final class SendMoneyCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();

        List<Transaction> transactionsArray = bank
                .getUserTransactionsDatabase().get(commandInput.getEmail());

        Transaction transactionSender = null;
        Transaction transactionReceiver = null;

        if (Utils.isValidIBAN(commandInput.getReceiver())
                && Utils.isValidIBAN(commandInput.getAccount())) {
            Account sender = bank.findAccountByIBAN(commandInput.getAccount());
            Account receiver = bank.findAccountByIBAN(commandInput.getReceiver());

            if (sender == null || receiver == null) {
                return;
            }

            if (sender.getBalance() < commandInput.getAmount()) {
                transactionSender = new InsufficientFundsError(commandInput.getTimestamp());
                transactionsArray.add(transactionSender);
                sender.getAccountTransactions().add(transactionSender);
                return;
            }

            String rateKey = sender.getCurrency() + "-" + receiver.getCurrency();
            Double rate = SimpleRateMapConverter.getRatesMap().get(rateKey);
            double convertedAmount = commandInput.getAmount() * rate;

            sender.subtractFunds(commandInput.getAmount());
            receiver.addFunds(convertedAmount);

            String senderAmount = commandInput.getAmount() + " " + sender.getCurrency();
            transactionSender = new SendMoneyTransaction(commandInput.getTimestamp(),
                    commandInput.getDescription(), sender.getIban(), receiver.getIban(),
                    senderAmount, "sent");

            String receiverAmount = convertedAmount + " " + receiver.getCurrency();
            transactionReceiver = new SendMoneyTransaction(commandInput.getTimestamp(),
                    commandInput.getDescription(), sender.getIban(), receiver.getIban(),
                    receiverAmount, "received");

            bank.getUserTransactionsDatabase()
                    .get(sender.getBelongsToEmail()).add(transactionSender);
            bank.getUserTransactionsDatabase()
                    .get(receiver.getBelongsToEmail()).add(transactionReceiver);

            sender.getAccountTransactions().add(transactionSender);
            receiver.getAccountTransactions().add(transactionReceiver);
        }
    }
}

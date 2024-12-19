package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandUtilities.BankingCommand;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionFactory;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;
import org.poo.utils.SimpleRateMapConverter;
import org.poo.utils.Utils;
import java.util.List;

/**
 * Comanda care permite transferul de bani intre doua conturi bancare.
 * Verifica daca exista suficiente fonduri in contul sursa si efectueaza transferul.
 */
public final class SendMoneyCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();

        List<Transaction> transactionsArray = bank
                .getUserTransactionsDatabase().get(commandInput.getEmail());

        Transaction transactionSender = null;
        Transaction transactionReceiver = null;
        TransactionData transactionData = null;

        if (Utils.isValidIBAN(commandInput.getReceiver())
                && Utils.isValidIBAN(commandInput.getAccount())) {
            Account sender = bank.findAccountByIBAN(commandInput.getAccount());
            Account receiver = bank.findAccountByIBAN(commandInput.getReceiver());

            if (sender == null || receiver == null) {
                return;
            }

            if (sender.getBalance() < commandInput.getAmount()) {
                transactionData = TransactionData.builder()
                        .timestamp(commandInput.getTimestamp())
                        .build();

                transactionSender = TransactionFactory
                        .createTransaction("InsufficientFundsError", transactionData);

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
            transactionData = TransactionData.builder()
                    .timestamp(commandInput.getTimestamp())
                    .description(commandInput.getDescription())
                    .senderIban(sender.getIban())
                    .receiverIban(receiver.getIban())
                    .sendMoneyAmount(senderAmount)
                    .transferType("sent")
                    .build();
            transactionSender = TransactionFactory
                    .createTransaction("SendMoneyTransaction", transactionData);

            String receiverAmount = convertedAmount + " " + receiver.getCurrency();
            transactionData.setSendMoneyAmount(receiverAmount);
            transactionData.setTransferType("received");
            transactionReceiver = TransactionFactory
                    .createTransaction("SendMoneyTransaction", transactionData);

            bank.getUserTransactionsDatabase()
                    .get(sender.getBelongsToEmail()).add(transactionSender);
            bank.getUserTransactionsDatabase()
                    .get(receiver.getBelongsToEmail()).add(transactionReceiver);

            sender.getAccountTransactions().add(transactionSender);
            receiver.getAccountTransactions().add(transactionReceiver);
        }
    }
}

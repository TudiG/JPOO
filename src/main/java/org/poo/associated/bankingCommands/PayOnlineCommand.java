package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandUtilities.BankingCommand;
import org.poo.associated.bankingCommands.commandUtilities.StaticOutputs;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionFactory;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.card.Card;
import org.poo.associated.transactionRelated.commerciantReport.CommerciantReport;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;
import org.poo.utils.SimpleRateMapConverter;
import org.poo.utils.Utils;
import java.util.List;

/**
 * Comanda pentru efectuarea unui plati online.
 * Verifica diverse conditii (card inghetat, fonduri insuficiente) si inregistreaza tranzactia.
 */
public final class PayOnlineCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();
        Card card = bank.findCardByNumber(commandInput.getCardNumber());

        if (card == null) {
            StaticOutputs.notFound(commandInput, Utils.CARD_NOT_FOUND, output);
            return;
        }

        Account account = bank.findAccountByCardNumber(card.getCardNumber());
        List<Transaction> transactionsArray = bank.getUserTransactionsDatabase()
                .get(account.getBelongsToEmail());

        Transaction transaction = null;
        TransactionData transactionData = null;

        if (bank.findCardByNumber(card.getCardNumber()).getStatus().equals("frozen")) {
            transactionData = TransactionData.builder()
                    .timestamp(commandInput.getTimestamp())
                    .build();

            transaction = TransactionFactory
                    .createTransaction("CardFrozenError", transactionData);

            transactionsArray.add(transaction);
            account.getAccountTransactions().add(transaction);
            return;
        }

        String rateKey = commandInput.getCurrency() + "-" + account.getCurrency();
        double rate = SimpleRateMapConverter.getRatesMap().get(rateKey);
        double convertedAmount = rate * commandInput.getAmount();

        if (account.getBalance() < convertedAmount) {
            transactionData = TransactionData.builder()
                    .timestamp(commandInput.getTimestamp())
                    .build();

            transaction = TransactionFactory
                    .createTransaction("InsufficientFundsError", transactionData);

            transactionsArray.add(transaction);
            account.getAccountTransactions().add(transaction);
            return;
        }

        account.subtractFunds(convertedAmount);

        transactionData = TransactionData.builder()
                .timestamp(commandInput.getTimestamp())
                .amount(convertedAmount)
                .commerciant(commandInput.getCommerciant())
                .build();

        transaction = TransactionFactory
                .createTransaction("PayOnlineTransaction", transactionData);

        CommerciantReport commerciantReport = new CommerciantReport(commandInput.getTimestamp(),
                convertedAmount, commandInput.getCommerciant(), transaction);

        transactionsArray.add(transaction);
        account.getAccountTransactions().add(transaction);
        account.getCommerciantInteractions().add(commerciantReport);

        if (card.isOneTimeCard()) {
            transactionData = TransactionData.builder()
                    .timestamp(commandInput.getTimestamp())
                    .cardNumber(card.getCardNumber())
                    .email(account.getBelongsToEmail())
                    .account(account.getIban())
                    .build();

            transaction = TransactionFactory
                    .createTransaction("CardDeletedTransaction", transactionData);

            transactionsArray.add(transaction);

            card.setCardNumber(Utils.generateCardNumber());

            transactionData.setCardNumber(card.getCardNumber());

            transaction = TransactionFactory
                    .createTransaction("NewCardTransaction", transactionData);

            transactionsArray.add(transaction);
        }
    }
}

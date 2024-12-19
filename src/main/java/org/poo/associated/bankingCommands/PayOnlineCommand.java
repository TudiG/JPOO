package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandUtilities.BankingCommand;
import org.poo.associated.bankingCommands.commandUtilities.StaticOutputs;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.card.Card;
import org.poo.associated.transactionRelated.CardDeletedTransaction;
import org.poo.associated.transactionRelated.CardFrozenError;
import org.poo.associated.transactionRelated.commerciantReport.CommerciantReport;
import org.poo.associated.transactionRelated.InsufficientFundsError;
import org.poo.associated.transactionRelated.NewCardTransaction;
import org.poo.associated.transactionRelated.PayOnlineTransaction;
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
        Account account = bank.findAccountByCardNumber(commandInput.getCardNumber());

        List<Transaction> transactionsArray = bank.getUserTransactionsDatabase()
                .get(commandInput.getEmail());

        Transaction transaction = null;

        if (account == null) {
            StaticOutputs.notFound(commandInput, Utils.CARD_NOT_FOUND, output);
            return;
        }

        String rateKey = commandInput.getCurrency() + "-" + account.getCurrency();
        double rate = SimpleRateMapConverter.getRatesMap().get(rateKey);
        double convertedAmount = rate * commandInput.getAmount();

        if (!bank.findUserByCardNumber(commandInput.getCardNumber()).getEmail()
                .equals(commandInput.getEmail())) {
            return;
        }

        if (bank.findCardByNumber(commandInput.getCardNumber()).getStatus().equals("frozen")) {
            transaction = new CardFrozenError(commandInput.getTimestamp());

            transactionsArray.add(transaction);
            account.getAccountTransactions().add(transaction);
            return;
        }

        if (account.getBalance() < convertedAmount) {
            transaction = new InsufficientFundsError(commandInput.getTimestamp());

            transactionsArray.add(transaction);
            account.getAccountTransactions().add(transaction);
            return;
        }

        account.subtractFunds(convertedAmount);

        transaction = new PayOnlineTransaction(commandInput.getTimestamp(),
                convertedAmount, commandInput.getCommerciant());
        CommerciantReport commerciantReport = new CommerciantReport(commandInput.getTimestamp(),
                convertedAmount, commandInput.getCommerciant(), transaction);

        transactionsArray.add(transaction);
        account.getAccountTransactions().add(transaction);
        account.getCommerciantInteractions().add(commerciantReport);

        Card card = bank.findCardByNumber(commandInput.getCardNumber());
        if (card.isOneTimeCard()) {
            transaction = new CardDeletedTransaction(commandInput.getTimestamp(),
                    card.getCardNumber(), commandInput.getEmail(), account.getIban());
            transactionsArray.add(transaction);

            card.setCardNumber(Utils.generateCardNumber());

            transaction = new NewCardTransaction(commandInput.getTimestamp(),
                    card.getCardNumber(), commandInput.getEmail(), account.getIban());
            transactionsArray.add(transaction);
        }
    }
}

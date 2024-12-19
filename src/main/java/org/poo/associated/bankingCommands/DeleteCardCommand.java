package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandUtilities.BankingCommand;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionFactory;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;

/**
 * Comanda pentru stergerea unui card.
 * Sterge cardul din contul asociat si inregistreaza o tranzactie de stergere a cardului.
 */
public final class DeleteCardCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();

        Account account = bank.findAccountByCardNumber(commandInput.getCardNumber());

        if (account == null) {
            return;
        }

        bank.deleteCardFromAccount(account.getBelongsToEmail(), commandInput.getCardNumber());

        TransactionData transactionData = TransactionData.builder()
                .timestamp(commandInput.getTimestamp())
                .cardNumber(commandInput.getCardNumber())
                .email(account.getBelongsToEmail())
                .account(account.getIban())
                .build();

        Transaction transaction = TransactionFactory
                .createTransaction("CardDeletedTransaction", transactionData);

        bank.getUserTransactionsDatabase().get(account.getBelongsToEmail()).add(transaction);
        account.getAccountTransactions().add(transaction);
    }
}

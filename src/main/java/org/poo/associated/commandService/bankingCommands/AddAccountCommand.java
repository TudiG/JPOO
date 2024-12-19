package org.poo.associated.commandService.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.commandService.commandUtilities.BankingCommand;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionFactory;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.accounts.accountUtilities.AccountFactory;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Comanda pentru a adauga un cont nou unui utilizator existent.
 * Aceasta comanda creeaza un cont nou si il asociaza unui utilizator.
 */
public final class AddAccountCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();
        Account account = AccountFactory.createAccount(commandInput, commandInput.getEmail());

        bank.getUsers().stream()
                .filter(user -> commandInput.getEmail().equals(user.getEmail()))
                .forEach(user -> user.getAccounts().add(account));

        TransactionData transactionData = TransactionData.builder()
                .timestamp(commandInput.getTimestamp())
                .build();

        Transaction transaction = TransactionFactory
                .createTransaction("AccountCreatedTransaction", transactionData);

        if (bank.getUserTransactionsDatabase().get(commandInput.getEmail()) == null) {
            List<Transaction> transactions = new ArrayList<>();

            transactions.add(transaction);
            bank.getUserTransactionsDatabase().put(account.getBelongsToEmail(), transactions);
        } else {
            bank.getUserTransactionsDatabase().get(account.getBelongsToEmail()).add(transaction);
        }

        account.getAccountTransactions().add(transaction);
    }
}

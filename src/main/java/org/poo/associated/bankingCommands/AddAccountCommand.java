package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.accounts.accountUtilities.AccountFactory;
import org.poo.associated.userRelated.transactions.AccountCreatedTransaction;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;

import java.util.ArrayList;
import java.util.List;

public final class AddAccountCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();
        Account account = AccountFactory.createAccount(commandInput, commandInput.getEmail());

        bank.getUsers().stream()
                .filter(user -> commandInput.getEmail().equals(user.getEmail()))
                .forEach(user -> user.getAccounts().add(account));

        Transaction transaction = new AccountCreatedTransaction(commandInput.getTimestamp());

        if (bank.getUserTransactionsDatabase().get(commandInput.getEmail()) == null) {
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);
            bank.getUserTransactionsDatabase().put(commandInput.getEmail(), transactions);
        } else {
            bank.getUserTransactionsDatabase().get(commandInput.getEmail()).add(transaction);
        }

        account.getAccountTransactions().add(transaction);
    }
}

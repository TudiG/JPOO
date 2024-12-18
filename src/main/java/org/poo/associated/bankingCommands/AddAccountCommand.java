package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.accounts.accountUtilities.AccountFactory;
import org.poo.associated.userRelated.transaction.AccountCreatedTransaction;
import org.poo.associated.userRelated.transaction.Transaction;
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
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        bank.getUserTransactionsDatabase().put(commandInput.getEmail(), transactions);
        account.getAccountTransactions().add(transaction);
    }
}

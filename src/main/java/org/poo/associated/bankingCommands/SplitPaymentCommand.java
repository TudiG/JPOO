package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.transactions.SplitPaymentError;
import org.poo.associated.userRelated.transactions.SplitPaymentTransaction;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;
import org.poo.utils.SimpleRateMapConverter;
import java.util.List;
import java.util.stream.Collectors;

public final class SplitPaymentCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();

        List<Account> payingAccounts = commandInput.getAccounts().stream()
                .map(bank::findAccountByIBAN)
                .collect(Collectors.toList());

        double splitAmount = commandInput.getAmount() / payingAccounts.size();

        for (Account account : payingAccounts.reversed()) {
            String rateKey = account.getCurrency() + "-" + commandInput.getCurrency();
            Double rate = SimpleRateMapConverter.getRatesMap().get(rateKey);
            double requiredAmountInAccountCurrency = splitAmount / rate;

            if (account.getBalance() < requiredAmountInAccountCurrency) {
                for (Account errorAccount : payingAccounts) {
                    List<Transaction> transactionArray = bank.getUserTransactionsDatabase()
                            .get(errorAccount.getBelongsToEmail());

                    String description = "Split payment of "
                            + String.format("%.2f", commandInput.getAmount())
                            + " " + commandInput.getCurrency();

                    Transaction transaction = new SplitPaymentError(commandInput.getTimestamp(),
                            description, commandInput.getCurrency(), splitAmount,
                            commandInput.getAccounts(), account.getIban());

                    transactionArray.add(transaction);
                    errorAccount.getAccountTransactions().add(transaction);
                }

                return;
            }
        }

        for (Account account : payingAccounts) {
            String rateKey = account.getCurrency() + "-" + commandInput.getCurrency();
            double rate = SimpleRateMapConverter.getRatesMap().get(rateKey);
            double requiredAmountInAccountCurrency = splitAmount / rate;

            account.subtractFunds(requiredAmountInAccountCurrency);

            List<Transaction> transactionArray = bank.getUserTransactionsDatabase()
                    .get(account.getBelongsToEmail());

            String description = "Split payment of "
                    + String.format("%.2f", commandInput.getAmount())
                    + " " + commandInput.getCurrency();

            Transaction transaction = new SplitPaymentTransaction(commandInput.getTimestamp(),
                    description, commandInput.getCurrency(), splitAmount,
                    commandInput.getAccounts());

            transactionArray.add(transaction);
            account.getAccountTransactions().add(transaction);
        }
    }
}

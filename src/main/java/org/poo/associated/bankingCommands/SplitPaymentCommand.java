package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandUtilities.BankingCommand;
import org.poo.associated.bankingCommands.commandUtilities.StaticOutputs;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.transactionRelated.SplitPaymentError;
import org.poo.associated.transactionRelated.SplitPaymentTransaction;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;
import org.poo.utils.SimpleRateMapConverter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Comanda pentru procesarea unei plata distribuite, in care o suma specificata
 * este impartita intre mai multe conturi. Daca un cont nu are fondurile necesare
 * pentru a acoperi partea sa din plata, se va adauga o tranzactie de eroare tuturor
 * participantilor.
 */
public final class SplitPaymentCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();

        List<Account> payingAccounts = commandInput.getAccounts().stream()
                .map(bank::findAccountByIBAN)
                .collect(Collectors.toList());

        double splitAmount = commandInput.getAmount() / payingAccounts.size();

        Account failedAccount = payingAccounts.reversed().stream()
                .filter(account -> {
                    String rateKey = account.getCurrency() + "-" + commandInput.getCurrency();
                    Double rate = SimpleRateMapConverter.getRatesMap().get(rateKey);
                    double requiredAmountInAccountCurrency = splitAmount / rate;

                    return account.getBalance() < requiredAmountInAccountCurrency;
                })
                .findFirst()
                .orElse(null);

        if (failedAccount != null) {
            payingAccounts.forEach(account -> {
                List<Transaction> transactionArray = bank.getUserTransactionsDatabase()
                        .get(account.getBelongsToEmail());

                String description = StaticOutputs.createDescription(commandInput);

                Transaction transaction = new SplitPaymentError(commandInput.getTimestamp(),
                        description, commandInput.getCurrency(), splitAmount,
                        commandInput.getAccounts(), failedAccount.getIban());

                transactionArray.add(transaction);
                account.getAccountTransactions().add(transaction);
            });
            return;
        }

        payingAccounts.forEach(account -> {
            String rateKey = account.getCurrency() + "-" + commandInput.getCurrency();
            double rate = SimpleRateMapConverter.getRatesMap().get(rateKey);
            double requiredAmountInAccountCurrency = splitAmount / rate;

            account.subtractFunds(requiredAmountInAccountCurrency);

            List<Transaction> transactionArray = bank.getUserTransactionsDatabase()
                    .get(account.getBelongsToEmail());

            String description = StaticOutputs.createDescription(commandInput);

            Transaction transaction = new SplitPaymentTransaction(commandInput.getTimestamp(),
                    description, commandInput.getCurrency(), splitAmount,
                    commandInput.getAccounts());

            transactionArray.add(transaction);
            account.getAccountTransactions().add(transaction);
        });
    }
}

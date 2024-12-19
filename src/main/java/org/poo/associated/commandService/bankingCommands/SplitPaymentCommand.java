package org.poo.associated.commandService.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.commandService.commandUtilities.BankingCommand;
import org.poo.associated.commandService.commandUtilities.StaticOutputs;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionFactory;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
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

                TransactionData transactionData = TransactionData.builder()
                        .timestamp(commandInput.getTimestamp())
                        .description(description)
                        .currency(commandInput.getCurrency())
                        .amount(splitAmount)
                        .involvedAccounts(commandInput.getAccounts())
                        .account(failedAccount.getIban())
                        .build();

                Transaction transaction = TransactionFactory
                        .createTransaction("SplitPaymentError", transactionData);

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

            TransactionData transactionData = TransactionData.builder()
                    .timestamp(commandInput.getTimestamp())
                    .description(description)
                    .currency(commandInput.getCurrency())
                    .amount(splitAmount)
                    .involvedAccounts(commandInput.getAccounts())
                    .build();

            Transaction transaction = TransactionFactory
                    .createTransaction("SplitPaymentTransaction", transactionData);

            transactionArray.add(transaction);
            account.getAccountTransactions().add(transaction);
        });
    }
}

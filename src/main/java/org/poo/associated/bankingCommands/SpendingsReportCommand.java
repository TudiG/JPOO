package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandUtilities.BankingCommand;
import org.poo.associated.bankingCommands.commandUtilities.StaticOutputs;
import org.poo.associated.userRelated.accounts.SavingsAccount;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.transactionRelated.commerciantReport.CommerciantReport;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Comanda pentru generarea unui raport de cheltuieli, care include tranzacțiile
 * cu comercianții pentru un anumit cont, într-un interval de timp specificat.
 */
public final class SpendingsReportCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Account account = Bank.getInstance().findAccountByIBAN(commandInput.getAccount());

        if (account == null) {
            StaticOutputs.notFound(commandInput, Utils.ACCOUNT_NOT_FOUND, output);
            return;
        }

        if (account.getClass().equals(SavingsAccount.class)) {
            StaticOutputs.isSavingsAccount(commandInput, output);
            return;
        }

        List<Transaction> transactions = new ArrayList<>();
        List<CommerciantReport> filteredCommerciants = new ArrayList<>();

        account.getCommerciantInteractions().forEach(commerciant -> {
            if (commerciant.getTimestamp() >= commandInput.getStartTimestamp()
                    && commerciant.getTimestamp() <= commandInput.getEndTimestamp()) {
                filteredCommerciants.add(commerciant);
                transactions.add(commerciant.getTransaction());
            }
        });

        filteredCommerciants.sort(Comparator.comparing(CommerciantReport::getCommerciant));

        StaticOutputs.printSpendingReport(commandInput, output,
                account, filteredCommerciants, transactions);
    }
}

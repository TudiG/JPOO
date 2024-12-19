package org.poo.associated.commandService.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.commandService.commandUtilities.BankingCommand;
import org.poo.associated.commandService.commandUtilities.StaticOutputs;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Comanda care genereaza un raport pentru un cont specificat.
 * Raportul include soldul contului, moneda, IBAN-ul si tranzactiile care au
 * avut loc intr-un interval de timp specificat.
 */
public final class ReportCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();
        Account account = bank.findAccountByIBAN(commandInput.getAccount());

        if (account == null) {
            StaticOutputs.notFound(commandInput, Utils.ACCOUNT_NOT_FOUND, output);
            return;
        }

        List<Transaction> filteredTransactions = new ArrayList<>();

        account.getAccountTransactions().forEach(transaction -> {
            if (transaction.getTimestamp() >= commandInput.getStartTimestamp()
                    && transaction.getTimestamp() <= commandInput.getEndTimestamp()) {
                filteredTransactions.add(transaction);
            }
        });

        StaticOutputs.printReport(commandInput, output, account, filteredTransactions);
    }
}

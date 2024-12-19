package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandUtilities.BankingCommand;
import org.poo.associated.bankingCommands.commandUtilities.StaticOutputs;
import org.poo.associated.userRelated.accounts.SavingsAccount;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.transactionRelated.InterestRateChangeTransaction;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;

/**
 * Comanda pentru a schimba rata dobanzii unui cont de economii.
 * Aceasta modifica rata dobanzii doar pentru conturile de economii si inregistreaza tranzactia.
 * In caz contrar, se afiseaza un mesaj de eroare.
 */
public final class ChangeInterestRateCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();
        Account account = bank.findAccountByIBAN(commandInput.getAccount());

        if (account == null) {
            return;
        }

        if (account.getClass().equals(SavingsAccount.class)) {
            SavingsAccount savingsAccount = (SavingsAccount) account;
            savingsAccount.updateInterestRate(commandInput.getInterestRate());

            Transaction transaction = new InterestRateChangeTransaction(commandInput.getTimestamp(),
                    commandInput.getInterestRate());

            bank.getUserTransactionsDatabase().get(account.getBelongsToEmail()).add(transaction);
            savingsAccount.getAccountTransactions().add(transaction);
        } else {
            StaticOutputs.isNotSavingsAccount(commandInput, output);
        }
    }
}

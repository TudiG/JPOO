package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandUtilities.BankingCommand;
import org.poo.associated.bankingCommands.commandUtilities.StaticOutputs;
import org.poo.associated.transactionRelated.AccountDeletedFundsError;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;

/**
 * Comanda pentru stergerea unui cont.
 * Contul poate fi sters doar daca are un sold de 0.
 * Daca contul nu poate fi sters, se inregistreaza o tranzactie de eroare.
 */
public final class DeleteAccountCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();

        bank.getUsers().stream()
                .filter(user -> commandInput.getEmail().equals(user.getEmail()))
                .findFirst()
                .ifPresentOrElse(user -> {
                    boolean removed = user.getAccounts().removeIf(account ->
                        commandInput.getAccount()
                                .equals(account.getIban()) && account.getBalance() == 0
                    );

                    if (!removed) {
                        Transaction transaction =
                                new AccountDeletedFundsError(commandInput.getTimestamp());
                        bank.getUserTransactionsDatabase()
                                .get(commandInput.getEmail()).add(transaction);
                    }

                    StaticOutputs.deleteAccountOutput(commandInput, output, removed);
                }, () -> {
                });
    }


}

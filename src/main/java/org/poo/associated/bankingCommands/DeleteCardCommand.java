package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.transactionRelated.CardDeletedTransaction;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;

/**
 * Comanda pentru stergerea unui card.
 * Sterge cardul din contul asociat si inregistreaza o tranzactie de stergere a cardului.
 */
public final class DeleteCardCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();

        Account accountToRemoveFrom = bank.findAccountByCardNumber(commandInput.getCardNumber());

        if (accountToRemoveFrom == null) {
            return;
        }

        bank.getUsers().stream()
                .filter(user -> commandInput.getEmail().equalsIgnoreCase(user.getEmail()))
                .findAny()
                .ifPresent(selectedUser -> selectedUser.getAccounts()
                        .forEach(account -> account.getCards()
                                .removeIf(card -> commandInput.getCardNumber()
                                        .equals(card.getCardNumber()))
                        )
                );

        Transaction transaction = new CardDeletedTransaction(commandInput.getTimestamp(),
                commandInput.getCardNumber(), commandInput.getEmail(),
                accountToRemoveFrom.getIban());

        bank.getUserTransactionsDatabase().get(commandInput.getEmail()).add(transaction);
        accountToRemoveFrom.getAccountTransactions().add(transaction);
    }
}

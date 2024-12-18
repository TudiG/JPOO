package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.transaction.CardDeletedTransaction;
import org.poo.associated.userRelated.transaction.Transaction;
import org.poo.fileio.CommandInput;

public final class DeleteCardCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();

        Account accountToRemoveFrom = bank.findAccountByCardNumber(commandInput.getCardNumber());

        if(accountToRemoveFrom == null) {
            return;
        }

        Bank.getInstance().getUsers().stream()
                .filter(user -> commandInput.getEmail().equalsIgnoreCase(user.getEmail()))
                .findAny()
                .ifPresent(selectedUser -> selectedUser.getAccounts()
                        .forEach(account -> account.getCards()
                                .removeIf(card -> commandInput.getCardNumber().equals(card.getCardNumber()))
                        )
                );

        Transaction transaction = new CardDeletedTransaction(commandInput.getTimestamp(),
                commandInput.getCardNumber(), commandInput.getEmail(), accountToRemoveFrom.getIBAN());

        bank.getUserTransactionsDatabase().get(commandInput.getEmail()).add(transaction);
        accountToRemoveFrom.getAccountTransactions().add(transaction);
    }
}

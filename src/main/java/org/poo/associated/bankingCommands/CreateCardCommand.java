package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.card.Card;
import org.poo.associated.userRelated.transactions.NewCardTransaction;
import org.poo.associated.userRelated.transactions.transactionUtilities.Transaction;
import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

public final class CreateCardCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();

        if (!bank.findAccountByIBAN(commandInput.getAccount()).getBelongsToEmail()
                .equals(commandInput.getEmail())) {
            return;
        }

        String cardNumber = Utils.generateCardNumber();

        bank.getUsers().stream()
                .filter(user -> commandInput.getEmail().equalsIgnoreCase(user.getEmail()))
                .findAny()
                .ifPresent(selectedUser -> selectedUser.getAccounts().stream()
                        .filter(account -> commandInput.getAccount().equals(account.getIban()))
                        .forEach(account -> account.getCards().add(new Card(cardNumber, false))));

        Transaction transaction = new NewCardTransaction(commandInput.getTimestamp(),
                cardNumber, commandInput.getEmail(), commandInput.getAccount());

        if (bank.getUserTransactionsDatabase().get(commandInput.getEmail()) == null) {
            return;
        }

        bank.getUserTransactionsDatabase().get(commandInput.getEmail()).add(transaction);
        bank.findAccountByCardNumber(cardNumber).getAccountTransactions().add(transaction);
    }
}

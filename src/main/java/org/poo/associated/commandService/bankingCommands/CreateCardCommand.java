package org.poo.associated.commandService.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.commandService.commandUtilities.BankingCommand;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionFactory;
import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

/**
 * Comanda pentru a crea un card nou pentru un utilizator.
 * Daca contul este asociat utilizatorului, se genereaza un numar de card si se adauga
 * in contul respectiv.
 * Se inregistreaza o tranzactie de creare a cardului.
 */
public final class CreateCardCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();

        if (!bank.findAccountByIBAN(commandInput.getAccount())
                .getBelongsToEmail().equals(commandInput.getEmail())) {
            return;
        }

        String cardNumber = Utils.generateCardNumber();

        bank.addCardToAccount(commandInput.getEmail(), commandInput.getAccount(),
                cardNumber, false);

        TransactionData transactionData = TransactionData.builder()
                .timestamp(commandInput.getTimestamp())
                .cardNumber(cardNumber)
                .email(commandInput.getEmail())
                .account(commandInput.getAccount())
                .build();

        Transaction transaction = TransactionFactory
                .createTransaction("NewCardTransaction", transactionData);

        bank.getUserTransactionsDatabase().get(commandInput.getEmail()).add(transaction);
        bank.findAccountByCardNumber(cardNumber).getAccountTransactions().add(transaction);
    }
}

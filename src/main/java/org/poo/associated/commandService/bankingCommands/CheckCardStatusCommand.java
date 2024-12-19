package org.poo.associated.commandService.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.commandService.commandUtilities.BankingCommand;
import org.poo.associated.commandService.commandUtilities.StaticOutputs;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionData;
import org.poo.associated.transactionRelated.transactionUtilities.TransactionFactory;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.card.Card;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.associated.userRelated.user.User;
import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

import java.util.List;

/**
 * Comanda pentru a verifica statusul unui card.
 * Daca cardul este gasit, se verifica daca balanta contului asociat este sub limita minima.
 * Daca da, cardul este inghetat si o tranzactie de avertizare este inregistrata.
 * In caz contrar, este anuntata eroarea cautarii unui cardul inexistent.
 */
public final class CheckCardStatusCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();
        Card card = bank.findCardByNumber(commandInput.getCardNumber());

        if (card == null) {
            StaticOutputs.notFound(commandInput, Utils.CARD_NOT_FOUND, output);
            return;
        }

        Account account = bank.findAccountByCardNumber(card.getCardNumber());
        User user = bank.findUserByCardNumber(commandInput.getCardNumber());

        List<Transaction> transactionArray = bank.getUserTransactionsDatabase()
                .get(user.getEmail());

        if (account.getMinimumBalance() >= account.getBalance()) {
            card.setStatus("frozen");

            TransactionData transactionData = TransactionData.builder()
                    .timestamp(commandInput.getTimestamp())
                    .build();

            Transaction transaction = TransactionFactory
                    .createTransaction("MinimumBalanceWarning", transactionData);

            transactionArray.add(transaction);
            account.getAccountTransactions().add(transaction);
        }
    }
}

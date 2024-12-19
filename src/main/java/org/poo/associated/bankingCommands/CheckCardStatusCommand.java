package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.card.Card;
import org.poo.associated.transactionRelated.MinimumBalanceWarning;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.associated.userRelated.user.User;
import org.poo.fileio.CommandInput;

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
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode fieldNode = mapper.createObjectNode();
            ObjectNode outputNode = mapper.createObjectNode();

            fieldNode.put("command", commandInput.getCommand());

            outputNode.put("description", "Card not found");
            outputNode.put("timestamp", commandInput.getTimestamp());

            fieldNode.set("output", outputNode);
            fieldNode.put("timestamp", commandInput.getTimestamp());

            output.add(fieldNode);
            return;
        }

        Account account = bank.findAccountByCardNumber(card.getCardNumber());

        User user = bank.findUserByCardNumber(commandInput.getCardNumber());

        List<Transaction> transactionArray = bank.getUserTransactionsDatabase()
                .get(user.getEmail());

        if (account.getMinimumBalance() >= account.getBalance()) {
            card.setStatus("frozen");

            Transaction transaction = new MinimumBalanceWarning(commandInput.getTimestamp());

            transactionArray.add(transaction);
            account.getAccountTransactions().add(transaction);
        }
    }
}

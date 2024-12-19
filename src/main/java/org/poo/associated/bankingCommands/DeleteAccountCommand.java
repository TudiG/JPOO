package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
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
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputNode = mapper.createObjectNode();
        ObjectNode fieldNode = mapper.createObjectNode();

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

                    fieldNode.put("command", commandInput.getCommand());

                    String operationResult =  removed ? "success" : "error";
                    String operationMessage = removed ? "Account deleted"
                            : "Account couldn't be deleted - see org.poo.transactions for details";

                    outputNode.put(operationResult, operationMessage);
                    outputNode.put("timestamp", commandInput.getTimestamp());

                    fieldNode.set("output", outputNode);
                    fieldNode.put("timestamp", commandInput.getTimestamp());

                    output.add(fieldNode);
                }, () -> {
                });
    }
}

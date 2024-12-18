package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.card.Card;
import org.poo.associated.userRelated.transaction.NewCardTransaction;
import org.poo.associated.userRelated.transaction.Transaction;
import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

public final class CreateCardCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Bank bank = Bank.getInstance();
        String cardNumber = Utils.generateCardNumber();

        bank.getUsers().stream()
                .filter(user -> commandInput.getEmail().equalsIgnoreCase(user.getEmail()))
                .findAny()
                .ifPresent(selectedUser -> selectedUser.getAccounts().stream()
                        .filter(account -> commandInput.getAccount().equals(account.getIBAN()))
                        .forEach(account -> account.getCards().add(new Card(cardNumber, false))));

        NewCardTransaction transaction = new NewCardTransaction(commandInput.getTimestamp(),
                cardNumber, commandInput.getEmail(), commandInput.getAccount());

        if(bank.getUserTransactionsDatabase().get(commandInput.getEmail()) == null) {
            return;
        }

        bank.getUserTransactionsDatabase().get(commandInput.getEmail()).add(transaction);
        bank.findAccountByCardNumber(cardNumber).getAccountTransactions().add(transaction);

//        fieldNode.put("account", commandInput.getAccount());
//        fieldNode.put("card", cardNumber);
//        fieldNode.put("cardHolder", commandInput.getEmail());
//        fieldNode.put("description", "New card created");
//        fieldNode.put("timestamp", commandInput.getTimestamp());
//
//        ArrayNode transactionArray = Bank.getInstance().getTransactionDatabase().get(commandInput.getEmail());
//        if(transactionArray != null) {
//            transactionArray.add(fieldNode);
//        }
//
//        //TEMP IMPLEM
//        if(Bank.getInstance().findAccountByIBAN(commandInput.getAccount()) == null) {
//            return;
//        }
//        Bank.getInstance().findAccountByIBAN(commandInput.getAccount()).getAccountTransactions().add(fieldNode);
    }
}

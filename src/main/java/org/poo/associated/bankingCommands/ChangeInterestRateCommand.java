package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
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
            bank.findAccountByIBAN(account.getIban()).getAccountTransactions().add(transaction);
        } else {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode fieldNode = mapper.createObjectNode();
            ObjectNode outputNode = mapper.createObjectNode();

            fieldNode.put("command", commandInput.getCommand());

            outputNode.put("description", "This is not a savings account");
            outputNode.put("timestamp", commandInput.getTimestamp());

            fieldNode.set("output", outputNode);
            fieldNode.put("timestamp", commandInput.getTimestamp());

            output.add(fieldNode);
        }
    }
}

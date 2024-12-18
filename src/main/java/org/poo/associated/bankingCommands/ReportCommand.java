package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.transaction.Transaction;
import org.poo.fileio.CommandInput;
import java.util.ArrayList;
import java.util.List;

public final class ReportCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();

        Account account = Bank.getInstance().findAccountByIBAN(commandInput.getAccount());

        if (account == null) {
            return;
        }

        Bank bank = Bank.getInstance();

        List<Transaction> filteredTransactions = new ArrayList<>();

        account.getAccountTransactions().forEach(transaction -> {
            if (transaction.getTimestamp() >= commandInput.getStartTimestamp() && transaction.getTimestamp() <= commandInput.getEndTimestamp()) {
                filteredTransactions.add(transaction);
            }
        });

        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("balance", account.getBalance());
        outputNode.put("currency", account.getCurrency());
        outputNode.put("IBAN", account.getIBAN());
        outputNode.put("transactions", mapper.valueToTree(filteredTransactions));

        fieldNode.put("command", commandInput.getCommand());
        fieldNode.set("output", outputNode);
        fieldNode.put("timestamp", commandInput.getTimestamp());

        output.add(fieldNode);
    }
}

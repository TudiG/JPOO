package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.CommandInput;

public class SpendingsReportCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();

        Account account = Bank.getInstance().findAccountByIBAN(commandInput.getAccount());

        if (account == null) {
            return;
        }

        ArrayNode transactionArray = account.getTransactions();

        ArrayNode filteredTransactions = mapper.createArrayNode();
        transactionArray.forEach(transaction -> {
            if (transaction.has("timestamp") && transaction.get("timestamp").asInt() >= commandInput.getStartTimestamp()
                    && transaction.get("timestamp").asInt() <= commandInput.getEndTimestamp()) {
                filteredTransactions.add(transaction);
            }
        });

        ArrayNode commerciantsArray = account.getCommerciants();


        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("balance", account.getBalance());
        outputNode.put("commerciants", commerciantsArray);
        outputNode.put("currency", account.getCurrency());
        outputNode.put("IBAN", account.getIBAN());
        outputNode.put("transactions", filteredTransactions);

        fieldNode.put("command", commandInput.getCommand());
        fieldNode.set("output", outputNode);
        fieldNode.put("timestamp", commandInput.getTimestamp());

        output.add(fieldNode);
    }
}

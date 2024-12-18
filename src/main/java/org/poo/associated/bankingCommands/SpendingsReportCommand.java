package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.commerciantReport.CommerciantReport;
import org.poo.associated.userRelated.transaction.Transaction;
import org.poo.fileio.CommandInput;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class SpendingsReportCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();

        Account account = Bank.getInstance().findAccountByIBAN(commandInput.getAccount());

        if (account == null) {
            return;
        }

        List<Transaction> transactions = new ArrayList<>();
        List<CommerciantReport> filteredCommerciants = new ArrayList<>();
        account.getCommerciantInteractions().forEach(commerciant -> {
            if (commerciant.getTimestamp() >= commandInput.getStartTimestamp() && commerciant.getTimestamp() <= commandInput.getEndTimestamp()) {
                filteredCommerciants.add(commerciant);
                transactions.add(commerciant.getTransaction());
            }
        });

        filteredCommerciants.sort(Comparator.comparingDouble(CommerciantReport::getTotal).reversed());

        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("balance", account.getBalance());
        outputNode.put("commerciants", mapper.valueToTree(filteredCommerciants));
        outputNode.put("currency", account.getCurrency());
        outputNode.put("IBAN", account.getIBAN());
        outputNode.put("transactions", mapper.valueToTree(transactions));

        fieldNode.put("command", commandInput.getCommand());
        fieldNode.set("output", outputNode);
        fieldNode.put("timestamp", commandInput.getTimestamp());

        output.add(fieldNode);
    }
}

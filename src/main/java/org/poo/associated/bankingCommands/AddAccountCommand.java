package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.accounts.accountUtilities.AccountFactory;
import org.poo.fileio.CommandInput;

public class AddAccountCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Account account = AccountFactory.createAccount(commandInput);

        Bank.getInstance().getUsers().stream()
                .filter(user -> commandInput.getEmail().equals(user.getEmail()))
                .forEach(user -> user.getAccounts().add(account));

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();

        fieldNode.put("timestamp", commandInput.getTimestamp());
        fieldNode.put("description", "New account created");

        ArrayNode accountsNode = mapper.createArrayNode();
        accountsNode.add(fieldNode);

        Bank.getInstance().getTransactionDatabase().put(commandInput.getEmail(), accountsNode);
    }
}

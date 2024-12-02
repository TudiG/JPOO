package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bank.Bank;
import org.poo.fileio.CommandInput;

public class AddFunds implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput,  final ArrayNode output) {
        Bank.getInstance().getUsers().stream()
                .flatMap(user -> user.getAccounts().stream())
                .filter(account -> commandInput.getAccount().equals(account.getIBAN()))
                .findFirst().stream()
                .findAny()
                .ifPresent(selectedAccount -> selectedAccount.addFund(commandInput.getAmount()));
    }
}

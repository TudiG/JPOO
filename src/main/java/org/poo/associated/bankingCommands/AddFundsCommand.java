package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.fileio.CommandInput;

/**
 * Comanda pentru a adauga fonduri intr-un cont specificat.
 * Aceasta comanda cauta contul specificat si adauga suma respectiva.
 */
public final class AddFundsCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput,  final ArrayNode output) {
        Bank.getInstance().getUsers().stream()
                .flatMap(user -> user.getAccounts().stream())
                .filter(account -> commandInput.getAccount().equals(account.getIban()))
                .findFirst().stream()
                .findAny()
                .ifPresent(selectedAccount -> selectedAccount.addFunds(commandInput.getAmount()));
    }
}

package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bank.Bank;
import org.poo.associated.userRelated.Card;
import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

public class CreateCard implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Card card = new Card(Utils.generateCardNumber(), "active");

        Bank.getInstance().getUsers().stream()
                .filter(user -> commandInput.getEmail().equalsIgnoreCase(user.getEmail()))
                .findAny()
                .ifPresent(selectedUser -> selectedUser.getAccounts().stream()
                        .filter(account -> commandInput.getAccount().equals(account.getIBAN()))
                        .forEach(account -> account.getCards().add(card)));
    }
}

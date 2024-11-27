package org.poo.associated;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import org.poo.associated.bank.Bank;
import org.poo.associated.bankingCommands.*;
import org.poo.fileio.CommandInput;

import java.util.HashMap;

@AllArgsConstructor
public class CommandService {
    private HashMap<String, BankingCommand> bankingCommands = new HashMap<>();

    public CommandService() {
        bankingCommands.put("printUsers", new PrintUsers());
        bankingCommands.put("createCard", new CreateCard());
        bankingCommands.put("addFunds", new AddFunds());
        bankingCommands.put("addAccount", new AddAccount());
    }

    public void executeCommands(CommandInput commandInput, Bank bank, ArrayNode output) {
        BankingCommand bankingCommand = bankingCommands.get(commandInput.getCommand());

        if(bankingCommand != null) {
            bankingCommand.execute(output);
        }
    }
}

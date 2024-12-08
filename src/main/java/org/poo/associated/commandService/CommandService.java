package org.poo.associated.commandService;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.*;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.fileio.CommandInput;

import java.util.HashMap;

@AllArgsConstructor
public class CommandService {
    private HashMap<String, BankingCommand> bankingCommands = new HashMap<>();
    private static CommandService serviceInstance;

    private CommandService() {
        bankingCommands.put("printUsers", new PrintUsersCommand());
        bankingCommands.put("createCard", new CreateCardCommand());
        bankingCommands.put("addFunds", new AddFundsCommand());
        bankingCommands.put("addAccount", new AddAccountCommand());
        bankingCommands.put("deleteAccount", new DeleteAccountCommand());
        bankingCommands.put("createOneTimeCard", new CreateOneTimeCard());
        bankingCommands.put("deleteCard", new DeleteCardCommand());
        bankingCommands.put("payOnline", new PayOnlineCommand());
        bankingCommands.put("sendMoney", new SendMoneyCommand());
    }

    public static synchronized CommandService getInstance() {
        if (serviceInstance == null) {
            serviceInstance = new CommandService();
        }

        return serviceInstance;
    }

    public void executeCommands(final CommandInput commandInput, final Bank bank, final ArrayNode output) {
        BankingCommand bankingCommand = bankingCommands.get(commandInput.getCommand());

        if(bankingCommand != null) {
            bankingCommand.execute(commandInput, output);
        }
    }
}

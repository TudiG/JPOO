package org.poo.associated.commandService;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.commandService.bankingCommands.AddAccountCommand;
import org.poo.associated.commandService.bankingCommands.AddFundsCommand;
import org.poo.associated.commandService.bankingCommands.AddInterestCommand;
import org.poo.associated.commandService.bankingCommands.ChangeInterestRateCommand;
import org.poo.associated.commandService.bankingCommands.CheckCardStatusCommand;
import org.poo.associated.commandService.bankingCommands.CreateCardCommand;
import org.poo.associated.commandService.bankingCommands.CreateOneTimeCard;
import org.poo.associated.commandService.bankingCommands.DeleteAccountCommand;
import org.poo.associated.commandService.bankingCommands.DeleteCardCommand;
import org.poo.associated.commandService.bankingCommands.PayOnlineCommand;
import org.poo.associated.commandService.bankingCommands.PrintTransactionsCommand;
import org.poo.associated.commandService.bankingCommands.PrintUsersCommand;
import org.poo.associated.commandService.bankingCommands.ReportCommand;
import org.poo.associated.commandService.bankingCommands.SendMoneyCommand;
import org.poo.associated.commandService.bankingCommands.SetAliasCommand;
import org.poo.associated.commandService.bankingCommands.SetMinimumBalanceCommand;
import org.poo.associated.commandService.bankingCommands.SpendingsReportCommand;
import org.poo.associated.commandService.bankingCommands.SplitPaymentCommand;
import org.poo.associated.commandService.commandUtilities.BankingCommand;
import org.poo.fileio.CommandInput;

import java.util.HashMap;

/**
 * Aceasta clasa contine toate comenzile care se dau pe parcursul
 * executarii programului.
 */
public final class CommandService {
    private static CommandService serviceInstance;
    private HashMap<String, BankingCommand> bankingCommands = new HashMap<>();

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
        bankingCommands.put("printTransactions", new PrintTransactionsCommand());
        bankingCommands.put("setAlias", new SetAliasCommand());
        bankingCommands.put("checkCardStatus", new CheckCardStatusCommand());
        bankingCommands.put("setMinimumBalance", new SetMinimumBalanceCommand());
        bankingCommands.put("changeInterestRate", new ChangeInterestRateCommand());
        bankingCommands.put("splitPayment", new SplitPaymentCommand());
        bankingCommands.put("report", new ReportCommand());
        bankingCommands.put("spendingsReport", new SpendingsReportCommand());
        bankingCommands.put("addInterest", new AddInterestCommand());
    }

    /**
     * Metoda pentru returnarea instantei Singleton.
     *
     * @return instanta clasei CommandService
     */
    public static synchronized CommandService getServiceInstance() {
        if (serviceInstance == null) {
            serviceInstance = new CommandService();
        }

        return serviceInstance;
    }

    /**
     * Metoda executa fiecare comanda, in funcite de comanda primita la intrare.
     * @param commandInput
     * @param output
     */
    public void executeCommands(final CommandInput commandInput, final ArrayNode output) {
        BankingCommand bankingCommand = bankingCommands.get(commandInput.getCommand());

        if (bankingCommand != null) {
            bankingCommand.execute(commandInput, output);
        }
    }
}

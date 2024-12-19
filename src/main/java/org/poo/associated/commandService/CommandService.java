package org.poo.associated.commandService;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.associated.bankingCommands.AddAccountCommand;
import org.poo.associated.bankingCommands.AddFundsCommand;
import org.poo.associated.bankingCommands.AddInterestCommand;
import org.poo.associated.bankingCommands.ChangeInterestRateCommand;
import org.poo.associated.bankingCommands.CheckCardStatusCommand;
import org.poo.associated.bankingCommands.CreateCardCommand;
import org.poo.associated.bankingCommands.CreateOneTimeCard;
import org.poo.associated.bankingCommands.DeleteAccountCommand;
import org.poo.associated.bankingCommands.DeleteCardCommand;
import org.poo.associated.bankingCommands.PayOnlineCommand;
import org.poo.associated.bankingCommands.PrintTransactionsCommand;
import org.poo.associated.bankingCommands.PrintUsersCommand;
import org.poo.associated.bankingCommands.ReportCommand;
import org.poo.associated.bankingCommands.SendMoneyCommand;
import org.poo.associated.bankingCommands.SetAliasCommand;
import org.poo.associated.bankingCommands.SetMinimumBalanceCommand;
import org.poo.associated.bankingCommands.SpendingsReportCommand;
import org.poo.associated.bankingCommands.SplitPaymentCommand;
import org.poo.associated.bankingCommands.commandUtilities.BankingCommand;
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
    public static synchronized CommandService getInstance() {
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

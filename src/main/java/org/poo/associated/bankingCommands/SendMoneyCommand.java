package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.CommandInput;
import org.poo.utils.SimpleRateMapConverter;

public class SendMoneyCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        Account receiver = Bank.getInstance().findAccountByIBAN(commandInput.getReceiver());
        Account sender = Bank.getInstance().findAccountByIBAN(commandInput.getAccount());

        if(receiver == sender) {
            return;
        }

        if(sender == null || receiver == null) {
            return;
        }

        if(sender.getBalance() < commandInput.getAmount()) {
            return;
        }

        String RateKey = sender.getCurrency() + "-" + receiver.getCurrency();
        double rate = SimpleRateMapConverter.ratesMap.get(RateKey);

        double sentAmount = commandInput.getAmount() * rate;

        sender.subtractFunds(commandInput.getAmount());
        receiver.addFunds(sentAmount);
    }
}

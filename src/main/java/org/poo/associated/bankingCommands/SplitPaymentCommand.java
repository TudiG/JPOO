package org.poo.associated.bankingCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.bankRelated.Bank;
import org.poo.associated.bankingCommands.commandInterface.BankingCommand;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.associated.userRelated.transaction.SplitPaymentTransaction;
import org.poo.associated.userRelated.transaction.Transaction;
import org.poo.fileio.CommandInput;
import org.poo.utils.SimpleRateMapConverter;

import javax.xml.crypto.dsig.TransformService;
import java.util.ArrayList;
import java.util.List;

public final class SplitPaymentCommand implements BankingCommand {
    @Override
    public void execute(final CommandInput commandInput, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode transactionNode = mapper.createObjectNode();

        Bank bank = Bank.getInstance();
        List<Account> payingAccounts = new ArrayList<>();

        for (String iban : commandInput.getAccounts()) {
            payingAccounts.add(bank.findAccountByIBAN(iban));
        }

        double splitAmount = commandInput.getAmount() / payingAccounts.size();

        for (Account account : payingAccounts) {
            String rateKey = account.getCurrency() + "-" + commandInput.getCurrency();
            Double rate = SimpleRateMapConverter.ratesMap.get(rateKey);
            double requiredAmountInAccountCurrency = splitAmount / rate;

            if (account.getBalance() < requiredAmountInAccountCurrency) {
                for (Account errorAccount : payingAccounts) {
                    List<Transaction> transactionArray = bank.getUserTransactionsDatabase().get(errorAccount.getBelongsToEmail());

//                    fieldNode.put("description", "Insufficient funds for split payment");
//                    fieldNode.put("timestamp", commandInput.getTimestamp());
//                    transactionArray.add(fieldNode);
                }
                return;
            }
        }

        for (Account account : payingAccounts) {
            String rateKey = account.getCurrency() + "-" + commandInput.getCurrency();
            double rate = SimpleRateMapConverter.ratesMap.get(rateKey);
            double requiredAmountInAccountCurrency = splitAmount / rate;

            account.subtractFunds(requiredAmountInAccountCurrency);

            List<Transaction> transactionArray = bank.getUserTransactionsDatabase().get(account.getBelongsToEmail());

            Transaction transaction = new SplitPaymentTransaction(commandInput.getTimestamp(), "Split payment of " + String.format("%.2f", commandInput.getAmount()) + " " + commandInput.getCurrency() , commandInput.getCurrency(), splitAmount, commandInput.getAccounts());

            transactionArray.add(transaction);
        }
    }
}

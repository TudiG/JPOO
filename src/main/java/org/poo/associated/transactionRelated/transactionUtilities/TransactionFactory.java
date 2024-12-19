package org.poo.associated.transactionRelated.transactionUtilities;

import org.poo.associated.transactionRelated.AccountCreatedTransaction;
import org.poo.fileio.CommandInput;

public final class TransactionFactory {
    private TransactionFactory() { }

    public static Transaction createTransaction(String transaction, TransactionData transactionData) {
        return switch (transaction) {
            case "AccountCreatedTransaction"-> new AccountCreatedTransaction(transactionData.getTimestamp());
            case "AccountDeletedFundsError" -> null;
            case "CardDeletedTransaction" -> null;
            case "CardFrozenError" -> null;
            case "InsufficientFundsError" -> null;
            case "InterestRateChangeTransaction" -> null;
            case "MinimumBalanceWarning" -> null;
            case "NewCardTransaction" -> null;
            case "PayOnlineTransaction" -> null;
            case "SendMoneyTransaction" -> null;
            case "SplitPaymentTransaction" -> null;
            case "SplitPaymentError" -> null;
            default -> throw new IllegalStateException("Unexpected input: " + transaction);
        };
    }
}

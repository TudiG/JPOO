package org.poo.associated.transactionRelated.transactionUtilities;

import org.poo.associated.transactionRelated.transactions.AccountCreatedTransaction;
import org.poo.associated.transactionRelated.transactions.AccountDeletedFundsError;
import org.poo.associated.transactionRelated.transactions.CardDeletedTransaction;
import org.poo.associated.transactionRelated.transactions.CardFrozenError;
import org.poo.associated.transactionRelated.transactions.InsufficientFundsError;
import org.poo.associated.transactionRelated.transactions.NewInterestRateTransaction;
import org.poo.associated.transactionRelated.transactions.MinimumBalanceWarning;
import org.poo.associated.transactionRelated.transactions.NewCardTransaction;
import org.poo.associated.transactionRelated.transactions.PayOnlineTransaction;
import org.poo.associated.transactionRelated.transactions.SendMoneyTransaction;
import org.poo.associated.transactionRelated.transactions.SplitPaymentError;
import org.poo.associated.transactionRelated.transactions.SplitPaymentTransaction;

public final class TransactionFactory {
    private TransactionFactory() { }

    /**
     * Metoda apelata pentru a instantia o tranzactie.
     * @param transaction reprezinta numele tranzactiei.
     * @param transactionData incapsuleaza toate datele necesare.
     * @return tranzactie dorita.
     */
    public static Transaction createTransaction(final String transaction,
                                                final TransactionData transactionData) {
        return switch (transaction) {
            case "AccountCreatedTransaction" -> new AccountCreatedTransaction(transactionData);
            case "AccountDeletedFundsError" -> new AccountDeletedFundsError(transactionData);
            case "CardDeletedTransaction" -> new CardDeletedTransaction(transactionData);
            case "CardFrozenError" -> new CardFrozenError(transactionData);
            case "InsufficientFundsError" -> new InsufficientFundsError(transactionData);
            case "NewInterestRateTransaction" -> new NewInterestRateTransaction(transactionData);
            case "MinimumBalanceWarning" -> new MinimumBalanceWarning(transactionData);
            case "NewCardTransaction" -> new NewCardTransaction(transactionData);
            case "PayOnlineTransaction" -> new PayOnlineTransaction(transactionData);
            case "SendMoneyTransaction" -> new SendMoneyTransaction(transactionData);
            case "SplitPaymentTransaction" -> new SplitPaymentTransaction(transactionData);
            case "SplitPaymentError" -> new SplitPaymentError(transactionData);
            default -> throw new IllegalStateException("Unexpected input: " + transaction);
        };
    }
}

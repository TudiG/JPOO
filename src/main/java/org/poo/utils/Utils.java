package org.poo.utils;

import java.util.Random;

public final class Utils {
    private Utils() {
        // Checkstyle error free constructor
    }

    public static final String CARD_NOT_FOUND = "Card not found";
    public static final String ACCOUNT_NOT_FOUND = "Account not found";

    private static final int IBAN_SEED = 1;
    private static final int CARD_SEED = 2;
    private static final int DIGIT_BOUND = 10;
    private static final int DIGIT_GENERATION = 16;
    private static final String RO_STR = "RO";
    private static final String POO_STR = "POOB";

    private static Random ibanRandom = new Random(IBAN_SEED);
    private static Random cardRandom = new Random(CARD_SEED);

    /**
     * Utility method for generating an IBAN code.
     *
     * @return the IBAN as String
     */
    public static String generateIBAN() {
        StringBuilder sb = new StringBuilder(RO_STR);
        for (int i = 0; i < RO_STR.length(); i++) {
            sb.append(ibanRandom.nextInt(DIGIT_BOUND));
        }

        sb.append(POO_STR);
        for (int i = 0; i < DIGIT_GENERATION; i++) {
            sb.append(ibanRandom.nextInt(DIGIT_BOUND));
        }

        return sb.toString();
    }

    /**
     * Utility method for generating a card number.
     *
     * @return the card number as String
     */
    public static String generateCardNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < DIGIT_GENERATION; i++) {
            sb.append(cardRandom.nextInt(DIGIT_BOUND));
        }

        return sb.toString();
    }

    /**
     * Resets the seeds between runs.
     */
    public static void resetRandom() {
        ibanRandom = new Random(IBAN_SEED);
        cardRandom = new Random(CARD_SEED);
    }

    /**
     * Metoda verifica daca String-ul trimis este defapt un alias, sau un IBAN normal.
     *
     * @param iban String-ul primit la intrare
     * @return true daca este un IBAN normal, altfel false in orice alt caz
     */
    public static boolean isValidIBAN(final String iban) {
        String ibanRegex = "^RO\\d{2}POOB\\d{16}$";
        return iban.matches(ibanRegex);
    }
}

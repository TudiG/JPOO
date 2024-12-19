package org.poo.associated.commandService.commandUtilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.associated.transactionRelated.commerciantReport.CommerciantReport;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;
import org.poo.fileio.CommandInput;
import java.util.List;

/**
 * Aceasta clasa exista pentru a muta procesul de serializare din comenzile
 * date la intrare intr-un loc mai "ascuns".
 *
 * @param <T> parametru generic folosit pentru liste cu diferite tipuri.
 */
public final class StaticOutputs<T> {
    private StaticOutputs() { }

    /**
     * Metoda apelata de comenzile care pot returna un mesaj de eroare
     * atunci cand nu poate fi gasit un cont sau un card.
     *
     * @param commandInput datele de la input.
     * @param description specifica exact ce nu s-a gasit.
     * @param output ArrayNode-ul afisat in fisier la iesire.
     */
    public static void notFound(final CommandInput commandInput,
                                final String description,
                                final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();
        ObjectNode outputNode = mapper.createObjectNode();

        fieldNode.put("command", commandInput.getCommand());

        outputNode.put("description", description);
        outputNode.put("timestamp", commandInput.getTimestamp());

        fieldNode.set("output", outputNode);
        fieldNode.put("timestamp", commandInput.getTimestamp());

        output.add(fieldNode);
    }

    /**
     * Metoda care afiseaza succes/eroarea data de incercarea stergerii
     * unui cont.
     *
     * @param commandInput datele de la input.
     * @param output ArrayNode-ul afisat in fisier la iesire.
     * @param removed boolean-ul care arata daca contul a fost sters sau nu.
     */
    public static void deleteAccountOutput(final CommandInput commandInput,
                                           final ArrayNode output,
                                           final boolean removed) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputNode = mapper.createObjectNode();
        ObjectNode fieldNode = mapper.createObjectNode();

        fieldNode.put("command", commandInput.getCommand());

        String operationResult =  removed ? "success" : "error";
        String operationMessage = removed ? "Account deleted"
                : "Account couldn't be deleted - see org.poo.transactions for details";

        outputNode.put(operationResult, operationMessage);
        outputNode.put("timestamp", commandInput.getTimestamp());

        fieldNode.set("output", outputNode);
        fieldNode.put("timestamp", commandInput.getTimestamp());

        output.add(fieldNode);
    }

    /**
     * Metoda apelata cand se incearca efectuarea unei operatii nepermise
     * pe un cont clasic.
     *
     * @param commandInput datele de la input.
     * @param output ArrayNode-ul afisat in fisier la iesire.
     */
    public static void isNotSavingsAccount(final CommandInput commandInput,
                                           final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();
        ObjectNode outputNode = mapper.createObjectNode();

        fieldNode.put("command", commandInput.getCommand());

        outputNode.put("description", "This is not a savings account");
        outputNode.put("timestamp", commandInput.getTimestamp());

        fieldNode.set("output", outputNode);
        fieldNode.put("timestamp", commandInput.getTimestamp());

        output.add(fieldNode);
    }

    /**
     * Metoda apelata cand se incearca afisarea unui spending report
     * pe un cont de economii.
     *
     * @param commandInput datele de la input.
     * @param output ArrayNode-ul afisat in fisier la iesire.
     */
    public static void isSavingsAccount(final CommandInput commandInput,
                                        final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();
        ObjectNode outputNode = mapper.createObjectNode();

        fieldNode.put("command", commandInput.getCommand());

        outputNode.put("error", "This kind of report is not supported for a saving account");

        fieldNode.set("output", outputNode);
        fieldNode.put("timestamp", commandInput.getTimestamp());

        output.add(fieldNode);
    }

    /**
     * Metoda folosita pentru a serializa diferite tipuri de liste.
     *
     * @param commandInput datele de la input.
     * @param output ArrayNode-ul afisat in fisier la iesire.
     * @param list lista care trebui serializata.
     * @param <T> tipul de obiecte din lista.
     */
    public static <T> void printArrayToOutput(final CommandInput commandInput,
                                              final ArrayNode output,
                                              final List<T> list) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();

        fieldNode.put("command", commandInput.getCommand());
        fieldNode.set("output", mapper.valueToTree(list));
        fieldNode.put("timestamp", commandInput.getTimestamp());

        output.add(fieldNode);
    }

    /**
     * Metoda apelata la creearea unui report pentru un cont.
     *
     * @param commandInput datele de la input.
     * @param output ArrayNode-ul afisat in fisier la iesire.
     * @param account contul care contine datele cerute.
     * @param filteredTransactions lista de tranzactii filtrate pe baza
     *                             unui timestamp de inceput si unul de
     *                             final.
     */
    public static void printReport(final CommandInput commandInput,
                                   final ArrayNode output,
                                   final Account account,
                                   final List<Transaction> filteredTransactions) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();
        ObjectNode outputNode = mapper.createObjectNode();

        outputNode.put("balance", account.getBalance());
        outputNode.put("currency", account.getCurrency());
        outputNode.put("IBAN", account.getIban());
        outputNode.put("transactions", mapper.valueToTree(filteredTransactions));

        fieldNode.put("command", commandInput.getCommand());
        fieldNode.set("output", outputNode);
        fieldNode.put("timestamp", commandInput.getTimestamp());

        output.add(fieldNode);
    }

    /**
     * Metoda apelata la creearea unui spending report pentru un cont clasic.
     *
     * @param commandInput datele de la input.
     * @param output ArrayNode-ul afisat in fisier la iesire.
     * @param account contul care contine datele cerute.
     * @param filteredCommerciants lista de comercianti filtrata pe baza
     *                             unui timestamp de inceput si unul de
     *                             final.
     * @param transactions lista formata dinamic pe baza obiectelor de tip
     *                     CommerciantReport.
     */
    public static void printSpendingReport(final CommandInput commandInput,
                                           final ArrayNode output,
                                           final Account account,
                                           final List<CommerciantReport> filteredCommerciants,
                                           final List<Transaction> transactions) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode fieldNode = mapper.createObjectNode();
        ObjectNode outputNode = mapper.createObjectNode();

        outputNode.put("balance", account.getBalance());
        outputNode.put("commerciants", mapper.valueToTree(filteredCommerciants));
        outputNode.put("currency", account.getCurrency());
        outputNode.put("IBAN", account.getIban());
        outputNode.put("transactions", mapper.valueToTree(transactions));

        fieldNode.put("command", commandInput.getCommand());
        fieldNode.set("output", outputNode);
        fieldNode.put("timestamp", commandInput.getTimestamp());

        output.add(fieldNode);
    }

    /**
     * O metda care construieste descrierea care este serailizata la comanda de
     * SplitPayment.
     *
     * @param commandInput date necesare data la input.
     * @return descrierea folosita la SplitPayment.
     */
    public static String createDescription(final CommandInput commandInput) {
        return "Split payment of "
                + String.format("%.2f", commandInput.getAmount())
                + " " + commandInput.getCurrency();
    }
}

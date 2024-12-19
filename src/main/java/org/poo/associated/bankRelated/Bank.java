package org.poo.associated.bankRelated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.poo.associated.userRelated.card.Card;
import org.poo.associated.transactionRelated.transactionUtilities.Transaction;
import org.poo.associated.userRelated.user.User;
import org.poo.associated.userRelated.accounts.accountUtilities.Account;

/**
 *  Clasa care retine toate informartiile de baza a programului,
 *  userii, alias-urile unui user si toate tranzactiile unui user.
 */
@Getter
public final class Bank {
    private static Bank bankInstance;
    private List<User> users;
    private Map<String, Alias> aliasDatabase;
    private Map<String, List<Transaction>> userTransactionsDatabase;

    private Bank() {
        aliasDatabase = new HashMap<>();
        userTransactionsDatabase = new HashMap<>();
    }

    /**
     * Metoda care returneaza instanta Signelton a bancii.
     *
     * @return instanta unica a clasei Bank
     */
    public static synchronized Bank getInstance() {
        if (bankInstance == null) {
            bankInstance = new Bank();
        }

        return bankInstance;
    }

    /**
     * Metoda care adauga referinta la userii bancii.
     *
     * @param collectedUsers lista de obiecte de tip User.
     */
    public void addAllUsers(final List<User> collectedUsers) {
        this.users = collectedUsers;
    }

    /**
     * Metod care cauta contul unui user pe baza unui email si al unui
     * IBAN dat la intrare pentru a adauga un card.
     *
     * @param email email-ul user-ului care adauga un card
     * @param iban iban-ul contului in care se adauga cardul
     * @param cardNumber numarul noului cardul generat
     * @param isOneTimeCard boolean-ul care determina tipul cardului
     */
    public void addCardToAccount(final String email, final String iban,
                                 final String cardNumber, final boolean isOneTimeCard) {
        users.stream()
                .filter(user -> email.equalsIgnoreCase(user.getEmail()))
                .findAny()
                .ifPresent(selectedUser -> selectedUser.getAccounts().stream()
                        .filter(account -> iban.equals(account.getIban()))
                        .forEach(account -> account.getCards()
                                .add(new Card(cardNumber, isOneTimeCard))));
    }

    /**
     * Metod care cauta contul unui user pe baza unui email si al unui
     * IBAN dat la intrare pentru a sterge un card.
     *
     * @param email email-ul user-ului care sterge un card
     * @param cardNumber numarul cardului care trebui sters
     */
    public void deleteCardFromAccount(final String email, final String cardNumber) {
        users.stream()
                .filter(user -> email.equalsIgnoreCase(user.getEmail()))
                .findAny()
                .ifPresent(selectedUser -> selectedUser.getAccounts()
                        .forEach(account -> account.getCards()
                                .removeIf(card -> cardNumber.equals(card.getCardNumber()))
                        )
                );
    }

    /**
     * Metoda folosita pentru a gasii un obiect User pe baza numarului cardului corespunzator.
     *
     * @param cardNumber numarul cardului de tip String pe baza caruia cautam user-ul.
     * @return Contul utilizatorului care detine cardul dat ca parametru.
     */
    public User findUserByCardNumber(final String cardNumber) {
        return users.stream()
                .filter(user -> user.getAccounts().stream()
                        .flatMap(account -> account.getCards().stream())
                        .anyMatch(card -> cardNumber.equals(card.getCardNumber())))
                .findFirst()
                .orElse(null);
    }

    /**
     * Metoda folosita pentru a gasii un obiect Account pe baza IBAN-ului corespunzator.
     *
     * @param iban IBAN-ul de tip String pe baza caruia cautam contul.
     * @return Contul care are IBAN-ul egal cu cel dat ca parametru.
     */
    public Account findAccountByIBAN(final String iban) {
        return users.stream()
                .flatMap(user -> user.getAccounts().stream())
                .filter(account -> iban.equals(account.getIban()))
                .findFirst().orElse(null);
    }

    /**
     * Metoda folosita pentru a gasii un obiect Account pe baza cardului corespunzator.
     *
     * @param cardNumber numarul cardului de tip String pe baza caruia cautam contul.
     * @return Obiectul de tip Account care are asociat cardul dat ca parametru.
     */
    public Account findAccountByCardNumber(final String cardNumber) {
        return users.stream()
                .flatMap(user -> user.getAccounts().stream())
                .filter(account -> account.getCards().stream()
                        .anyMatch(card -> cardNumber.equals(card.getCardNumber())))
                .findFirst()
                .orElse(null);
    }

    /**
     * Metoda folosita pentru a gasii un obiect Card pe baza numarului corespunzator.
     *
     * @param cardNumber numarul carduli de tip String pe baza caruia cautam obiectul corespunzator.
     * @return Obiectul de tip Card cautat.
     */
    public Card findCardByNumber(final String cardNumber) {
        return users.stream()
                .flatMap(user -> user.getAccounts().stream())
                .flatMap(account -> account.getCards().stream())
                .filter(card -> cardNumber.equals(card.getCardNumber()))
                .findFirst()
                .orElse(null);
    }
 }

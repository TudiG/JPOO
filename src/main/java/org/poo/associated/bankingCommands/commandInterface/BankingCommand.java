package org.poo.associated.bankingCommands.commandInterface;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.CommandInput;

public interface BankingCommand {
    /**
     * Metoda care va fi implementat de toate clasele care
     * reprezinta o comanda data in timpul rularii programului.
     *
     * @param commandInput datele comenzii primite
     * @param output lista rezultatelor serializate
     */
    void execute(CommandInput commandInput, ArrayNode output);
}

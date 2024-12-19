package org.poo.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import java.io.IOException;

/**
 * Aceasta clasa doar asigura o indentare asemanatoare cu cea din fisierele ref
 * pentru a permite compararea usoara intre output si referinta la prima vedere.
 */
public final class CustomPrettyPrinter extends DefaultPrettyPrinter {
    public CustomPrettyPrinter() {
        super();
        this._arrayIndenter = new DefaultIndenter("  ", "\n");
        this._objectIndenter = new DefaultIndenter("  ", "\n");
    }

    @Override
    public DefaultPrettyPrinter createInstance() {
        return new CustomPrettyPrinter();
    }

    @Override
    public void writeObjectFieldValueSeparator(final JsonGenerator g) throws IOException {
        g.writeRaw(": ");  // No space after colon
    }
}

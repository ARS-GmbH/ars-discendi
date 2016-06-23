/**
 *
 */
package de.ars.skilldb.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.ars.skilldb.interactor.Interactor;
import de.ars.skilldb.util.Ensure;

/**
 * Exception, die geworfen wird, wenn einem {@link Interactor} ein ungültiges Model übergeben wird.
 */
public class InvalidModelException extends RuntimeException {

    private static final long serialVersionUID = -9118993872910472866L;
    private static final Logger LOGGER = Logger.getLogger(InvalidModelException.class.getName());
    private final Map<String, String> failures;

    /**
     * Erzeugt eine neue {@link InvalidModelException} mit mehreren Fehlernachrichten, die durch den {@link Builder} hinugefügt werden.
     *
     * @param builder
     *            der {@link Builder}, der die Fehlernachrichten enthält.
     */
    public InvalidModelException(final Builder builder) {
        super(builder.message + builder.getReadableFailures(), builder.cause);
        failures = builder.failures;
    }

    /**
     * Erzeugt eine neue {@link InvalidModelException} mit einer Nachricht.
     *
     * @param message
     *            die Nachricht.
     */
    public InvalidModelException(final String message) {
        super(message);
        failures = new LinkedHashMap<>();
        log(this);
    }

    /**
     * Erzeugt eine neue {@link InvalidModelException} mit einer Nachricht und der ursprünglichen
     * Exception.
     *
     * @param message
     *            die Nachricht
     * @param cause
     *            die ursprüngliche Exception.
     */
    public InvalidModelException(final String message, final Throwable cause) {
        super(message, cause);
        failures = new LinkedHashMap<>();
        log(this);
    }

    @Override
    public String toString() {
        return getMessage() + getReadableFailures(failures);
    }

    public Map<String, String> getFailures() {
        return failures;
    }

    private static void log(final Throwable exception) {
        LOGGER.log(Level.WARNING, "The model has invalid properties.", exception);
    }

    private static String getReadableFailures(final Map<String, String> failures) {
        StringBuilder builder = new StringBuilder();
        if (!failures.isEmpty()) {
            builder.append("\n ##### These model errors were detected: ").append(System.lineSeparator());
            for (Entry<String, String> entry : failures.entrySet()) {
                builder.append(entry.getKey()).append(":\t").append(entry.getValue()).append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    /**
     * Builder für die {@link InvalidModelException}.
     */
    public static class Builder {

        private String message;
        private Throwable cause;
        private final Map<String, String> failures;

        /**
         * Erzeugt einen neuen Builder.
         */
        public Builder() {
            failures = new LinkedHashMap<>();
        }

        /**
         * Fügt eine Exception-Nachricht hinzu.
         *
         * @param msg
         *            die Nachricht.
         * @return den Builder.
         */
        public Builder message(final String msg) {
            message = msg;
            return this;
        }

        /**
         * Fügt eine Exception-Ursache hinzu.
         *
         * @param throwable
         *            die Ursache.
         * @return den Builder.
         */
        public Builder cause(final Throwable throwable) {
            cause = throwable;
            return this;
        }

        /**
         * Fügt einen Fehler für ein Attribut einer Model-Klasse hinzu.
         *
         * @param field
         *            der Name des Felds, wo der Fehler aufgetaucht ist. Darf nicht {@code null}
         *            sein.
         * @param msg
         *            die zu dem Fehler gehörende Nachricht.
         * @return den Builder.
         */
        public Builder withModelError(final String field, final String msg) {
            Ensure.that(field).isNotBlank();
            failures.put(field, msg);
            return this;
        }

        /**
         * Wirft die mit diesem Builder erzeugte {@link InvalidModelException}.
         */
        public void throwIt() {
            InvalidModelException exception = new InvalidModelException(this);
            log(exception);
            throw exception;
        }

        /**
         * Gibt zurück, ob Model-Fehler existieren.
         *
         * @return {@code true} wenn es Model-Fehler gibt, andernfalls {@code false}.
         */
        public boolean hasModelErrors() {
            return !failures.isEmpty();
        }

        private String getReadableFailures() {
            return InvalidModelException.getReadableFailures(failures);
        }
    }
}

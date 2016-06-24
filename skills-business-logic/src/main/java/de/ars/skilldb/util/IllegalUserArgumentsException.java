package de.ars.skilldb.util;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Wird geworfen, wenn eine Benutzereingabe falsch ist.
 */
public final class IllegalUserArgumentsException extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "An invalid user input caused an error.";
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(IllegalUserArgumentsException.class.getName());
    private final Map<String, String> errorMap;

    /**
     * Erstellt eine neue @link{IllegalUserArgumentsException}. Der Key enthält
     * den Namen des Attributs, zu dem ein fehlerhafter Wert eingegeben wurde.
     * Value enthält die detaillierte Fehlerbeschreibung.
     *
     * @param key
     *            Name des Attributs, zu dem die fehlerhafte Eingabe getätigt
     *            wurde
     * @param value
     *            Detaillierte Fehlerbeschreibung
     */
    public IllegalUserArgumentsException(final String key, final String value) {
        super(ERROR_MESSAGE);
        errorMap = new HashMap<String, String>();
        errorMap.put(key, value);
        logErrorDetails(key, value);
        LOGGER.log(Level.WARNING, getMessage(), this);
    }

    /**
     * Erstellt eine neue {@link IllegalUserArgumentsException}. Der Key enthält
     * den Namen des Attributs, zu dem ein fehlerhafter Wert eingegeben wurde.
     * Value enthält die detaillierte Fehlerbeschreibung.
     *
     * @param key
     *            Name des Attributs, zu dem die fehlerhafte Eingabe getätigt
     *            wurde
     * @param message
     *            Detaillierte {@link Formatter formatierte} Fehlerbeschreibung
     * @param args
     *            Argumente, die in der formatierten Nachricht verwendet werden.
     *            Wenn mehr Objekte übergeben werden als in der Nachricht
     *            angegeben, werden die übrigen ignoriert. Kann auch
     *            <tt> null </tt> sein.
     */
    public IllegalUserArgumentsException(final String key, final String message, final Object... args) {
        super(ERROR_MESSAGE);
        String errorMessage = String.format(message, args);
        errorMap = new HashMap<String, String>();
        errorMap.put(key, errorMessage);
        logErrorDetails(key, errorMessage);
        LOGGER.log(Level.WARNING, getMessage(), this);
    }

    /**
     * Erstellt eine neue @link{IllegalUserArgumentsException}. Die Map enthält
     * als Key den Namen des Attributs, zu dem ein fehlerhafter Wert eingegeben
     * wurde. Der entsprechende Value enthält die detaillierte
     * Fehlerbeschreibung.
     *
     * @param errorMap
     *            Enthält die Fehlernachricht. Key: Name des betroffenen
     *            Attributs. Value: detaillierte Fehlerbeschreibung
     */
    public IllegalUserArgumentsException(final Map<String, String> errorMap) {
        super(ERROR_MESSAGE);
        this.errorMap = errorMap;
        log(errorMap);
    }

    public Map<String, String> getAttributeMap() {
        return errorMap;
    }

    /**
     * Liefert eine Fehlermeldung zurück, die alle Fehler zusammenfasst.
     *
     * @return Fehlermeldung
     */
    public String getErrorDescription() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : errorMap.entrySet()) {
            builder.append(entry.getValue());
            builder.append("<br/>");
        }

        return builder.toString();
    }

    private void log(final Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            logErrorDetails(entry.getKey(), entry.getValue());
        }
        LOGGER.log(Level.WARNING, getMessage(), this);
    }

    private void logErrorDetails(final String key, final String value) {
        String message = String.format("%s: %s", key, value);
        LOGGER.log(Level.WARNING, message);
    }
}

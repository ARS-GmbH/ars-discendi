/*
 * Copyright (c) 2012/2013 Prof. Dr. Ullrich Hafner
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package de.ars.skilldb.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.CheckForNull;

/**
 * Thrown to indicate that a contract assertion check has been failed.
 *
 * @author Ulli Hafner
 */
public final class AssertionFailedException extends IllegalArgumentException {

    private static final long serialVersionUID = -7033759120346380864L;
    private static final Logger LOGGER = Logger.getLogger(AssertionFailedException.class.getName());

    /**
     * Constructs an {@link AssertionFailedException} with the specified
     * detail message.
     *
     * @param message
     *            the detail error message.
     */
    public AssertionFailedException(@CheckForNull final String message) {
        super(message);

        log(this);
    }

    /**
     * Constructs an {@link AssertionFailedException} with the specified
     * detail message and cause.
     *
     * @param message
     *            the detail error message.
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public AssertionFailedException(@CheckForNull final String message, @CheckForNull final Throwable cause) {
        super(message, cause);

        log(cause);
    }

    private static void log(final Throwable exception) {
        LOGGER.log(Level.WARNING, "Assertion failed.", exception);
    }

}

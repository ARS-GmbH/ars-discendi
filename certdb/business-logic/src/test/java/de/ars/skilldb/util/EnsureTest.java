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
package de.ars.skilldb.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.ars.skilldb.exception.AssertionFailedException;

/**
 * Tests the class {@link Ensure}.
 *
 * @author Ulli Hafner
 */
@SuppressWarnings("nls")
public class EnsureTest {
    private static final String NOT_EMPTY_STRING = "-";
    private static final String EMPTY_STRING = "";
    private static final String ERROR_MESSAGE = "Expected Error.";

    /**
     * Checks whether no exception is thrown if we adhere to all contracts.
     */
    @Test
    public void testCheckValidContracts() {
        Ensure.that(false).isFalse();
        Ensure.that(true).isTrue();
        Ensure.that(EMPTY_STRING).isNotNull();
        Ensure.that(EMPTY_STRING, EMPTY_STRING).isNotNull();
        Ensure.that(null, (Object) null).isNull();
        Ensure.that(new String[] {EMPTY_STRING}).isNotEmpty();
        Ensure.that(NOT_EMPTY_STRING).isNotEmpty();
        Ensure.that(NOT_EMPTY_STRING).isNotBlank();
        Ensure.that(EMPTY_STRING).isInstanceOf(String.class);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testExceptionIsNeverThrown() {
        Ensure.that(new IllegalArgumentException()).isNeverThrown(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireFalseThrowsException() {
        Ensure.that(true).isFalse(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testFail() {
        Ensure.thatStatementIsNeverReached();
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testFailWithMessage() {
        Ensure.thatStatementIsNeverReached(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireTrueThrowsException() {
        Ensure.that(false).isTrue(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireNullThrowsException() {
        Ensure.that(EMPTY_STRING).isNull(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireNullsThrowsException() {
        Ensure.that(EMPTY_STRING, EMPTY_STRING).isNull(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireNotNullThrowsException() {
        Ensure.that((Object) null).isNotNull(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireNotNulls1ThrowsException() {
        Ensure.that(EMPTY_STRING, (Object) null).isNotNull(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireNotNulls2ThrowsException() {
        Ensure.that(null, EMPTY_STRING).isNotNull(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireNotNulls3ThrowsException() {
        Ensure.that(null, (Object) null).isNotNull(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireNotEmptyArray() {
        Ensure.that(new String[0]).isNotEmpty(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireNotNullArray() {
        Ensure.that((Object[]) null).isNotEmpty(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireNotNullInCollection() {
        Ensure.that(Lists.newArrayList(EMPTY_STRING, null, EMPTY_STRING)).isNotEmpty(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireNotNullElementInArray() {
        Ensure.that(new String[] {EMPTY_STRING, null, EMPTY_STRING}).isNotEmpty(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireNotNullString() {
        Ensure.that((String) null).isNotEmpty(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireNotEmptyString() {
        Ensure.that(EMPTY_STRING).isNotEmpty(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireNotBlankString() {
        Ensure.that(" ").isNotBlank(ERROR_MESSAGE);
    }

    /**
     * Checks whether we throw an exception if a contract is violated.
     */
    @Test(expected = AssertionFailedException.class)
    public void testRequireInstanceOf() {
        Ensure.that(EMPTY_STRING).isInstanceOf(Integer.class, ERROR_MESSAGE);
    }

    /**
     * Verifies that the message format is correctly interpreted.
     */
    @Test
    public void testMessageFormat() {
        String message = null;
        try {
            Ensure.that(EMPTY_STRING).isInstanceOf(Integer.class, "This error uses '%s' to print the number %d.",
                    "String.format", 42);
        }
        catch (AssertionFailedException exception) {
            message = exception.getMessage();
        }

        assertEquals("Wrong message concatenation: ", "This error uses 'String.format' to print the number 42.",
                message);
    }
}
/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.parser.util;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

/**
 * Assertion class which abstracts use of existing assertion APIs from external
 * libraries. Copied from and/or inspired by
 * {@linkplain org.springframework.util.Assert}.
 *
 * @author mlipper
 * @since 2.0
 * @see org.springframework.util.Assert
 * @see org.apache.commons.lang3.StringUtils
 */
public class Assert {

    private Assert() {
    }

    /**
     * Validates that the provided {@link Collection} is not null and not empty. If
     * validation fails, the provided message is used to construct and throw an
     * {@link IllegalArgumentException}.
     *
     * @param collection the collection to verify is not null or empty
     * @param message    the message to use if the collection is null or empty
     * @throws IllegalArgumentException if {@code collection} is null or empty using
     *                                  the provided {@code message}
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (null == collection || collection.isEmpty()) {
            throw new IllegalArgumentException("Collection argument cannot be empty");
        }
    }

    /**
     * Assert a boolean expression, throwing an {@code IllegalArgumentException} if
     * the expression evaluates to {@code false}.
     *
     * <pre class="code">
     * Assert.isTrue(i &gt; 0, "The value must be greater than zero");
     * </pre>
     *
     * @param expression a boolean expression
     * @param message    the exception message to use if the assertion fails
     * @throws IllegalArgumentException if {@code expression} is {@code false}
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that the given String contains valid text content; that is, it must
     * not be {@code null} and must contain at least one non-whitespace character.
     *
     * <pre class="code">
     * Assert.hasText(name, "'name' must not be empty");
     * </pre>
     *
     * @param text    the String to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the text does not contain valid text
     *                                  content
     * @see StringUtils#isBlank
     */
    public static void hasText(String text, String message) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an object is not {@code null}.
     *
     * <pre class="code">
     * Assert.notNull(clazz, "The class must not be null");
     * </pre>
     *
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is {@code null}
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

}

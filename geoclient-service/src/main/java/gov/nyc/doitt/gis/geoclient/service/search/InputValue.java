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
package gov.nyc.doitt.gis.geoclient.service.search;

import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;
import gov.nyc.doitt.gis.geoclient.util.Assert;

/**
 * Value class used to retain metadata about values used to create search
 * request arguments. Specifically, this class tracks whether an input value
 * originated from the parser ({@link ValueSource#PARSED}) verbatim, was from
 * the parser but mapped to another value ({@link ValueSource#MAPPED}) or was
 * assigned by the search because it was missing ({@link ValueSource#ASSIGNED}).
 * Classification of {@link ValueSource} is based on which constructor was used
 * to create this instance.
 *
 * This class also tracks whether the value is considered to be resolved (e.g.
 * usable as a search argument) based on the following criteria:
 * <ul>
 * <li>If this object is not {@link ValueSource#MAPPED}, it is considered
 * resolved.</li>
 * <li>If this object is {@link ValueSource#MAPPED} and the {@link #mappedValue}
 * is not null, it is considered resolved.</li>
 * <li>If this object is {@link ValueSource#MAPPED} and the {@link #mappedValue}
 * is null, it is considered unresolved.</li>
 * </ul>
 *
 * @author mlipper
 *
 */
public class InputValue
{
    private final String originalValue;
    private final String mappedValue;
    private final TokenType tokenType;
    private final ValueSource valueSource;

    /**
     * Create an InputValue classified as {@link ValueSource#ASSIGNED}. This
     * instance will be considered resolved.
     *
     * @param tokenType
     *            TokenType
     * @param assignedValue
     *            value
     */
    public InputValue(TokenType tokenType, String assignedValue)
    {
        super();
        Assert.notNull(tokenType, "tokenType argument cannot be null.");
        this.tokenType = tokenType;
        Assert.notNull(assignedValue, "originalValue argument cannot be null.");
        this.originalValue = assignedValue;
        this.mappedValue = assignedValue;
        this.valueSource = ValueSource.ASSIGNED;
    }

    /**
     * Create an InputValue classified as {@link ValueSource#PARSED}. This
     * instance will be considered resolved.
     *
     * @param token
     *            unmodified Token from the parser
     */
    public InputValue(Token token)
    {
        Assert.notNull(token, "token argument cannot be null.");
        this.originalValue = token.getValue();
        this.mappedValue = null;
        this.tokenType = token.getType();
        this.valueSource = ValueSource.PARSED;
    }

    /**
     * Create an InputValue classified as {@link ValueSource#MAPPED}. If the
     * mappedValue argument is not null, this instance will be considered
     * resolved, otherwise it is unresolved.
     *
     * @param token
     *            unmodified parser Token
     * @param mappedValue
     *            value mapped to this Token
     */
    public InputValue(Token token, String mappedValue)
    {
        Assert.notNull(token, "token argument cannot be null.");
        this.originalValue = token.getValue();
        this.mappedValue = mappedValue;
        this.tokenType = token.getType();
        this.valueSource = ValueSource.MAPPED;
    }

    public boolean isResolved()
    {
        if (!this.isMapped())
        {
            return true;
        }
        return this.mappedValue != null;
    }

    public boolean isParsed()
    {
        return ValueSource.PARSED.equals(this.valueSource);
    }

    public boolean isMapped()
    {
        return ValueSource.MAPPED.equals(this.valueSource);
    }

    public boolean isAssigned()
    {
        return ValueSource.ASSIGNED.equals(this.valueSource);
    }

    public String getValue()
    {
        return mappedValue != null ? mappedValue : originalValue;
    }

    public String getOriginalValue()
    {
        return originalValue;
    }

    public TokenType getTokenType()
    {
        return tokenType;
    }

    public ValueSource getValueSource()
    {
        return valueSource;
    }

    public String description()
    {
        if (!isMapped())
        {
            return String.format("Token type %s with value '%s'", this.tokenType, this.originalValue);
        }
        return String.format("Token type %s with value '%s' mapped to value %s", this.tokenType, this.originalValue,
                this.mappedValue);
    }

    @Override
    public String toString()
    {
        return "InputValue [originalValue=" + originalValue + ", mappedValue=" + mappedValue + ", tokenType="
                + tokenType + ", valueSource=" + valueSource + "]";
    }

}

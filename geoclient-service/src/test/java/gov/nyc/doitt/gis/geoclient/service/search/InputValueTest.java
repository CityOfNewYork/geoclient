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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

public class InputValueTest
{

    @Test
    public void testInputValue_tokenAndMappedValueArg()
    {
        InputValue res = new InputValue(new Token(TokenType.CITY_NAME, "LIC",0,3),"QUEENS");
        assertThat(res.getOriginalValue()).isEqualTo("LIC");
        assertThat(res.getTokenType()).isEqualTo(TokenType.CITY_NAME);
        assertThat(res.getValue()).isEqualTo("QUEENS");
        assertThat(res.isMapped()).isTrue();
        assertThat(res.isAssigned()).isFalse();
        assertThat(res.isParsed()).isFalse();
        assertThat(res.isResolved()).isTrue();
    }

    @Test
    public void testTokenMapping_tokenAndNullMappedValue()
    {
        InputValue res = new InputValue(new Token(TokenType.CITY_NAME, "LIC",0,3),null);
        assertThat(res.getOriginalValue()).isEqualTo("LIC");
        assertThat(res.getTokenType()).isEqualTo(TokenType.CITY_NAME);
        assertThat(res.getValue()).isEqualTo("LIC");
        assertThat(res.isMapped()).isTrue();
        assertThat(res.isAssigned()).isFalse();
        assertThat(res.isParsed()).isFalse();
        assertThat(res.isResolved()).isFalse();
    }

    @Test
    public void testTokenMapping_nullTokenAndMappedValueArg()
    {
        Token nullToken = null;
        assertThrows(IllegalArgumentException.class, () -> {
            new InputValue(nullToken,"duh");
        });
    }

    @Test
    public void testInputValue_tokenArg()
    {
        InputValue res = new InputValue(new Token(TokenType.CITY_NAME, "LIC",0,3));
        assertThat(res.getOriginalValue()).isEqualTo("LIC");
        assertThat(res.getTokenType()).isEqualTo(TokenType.CITY_NAME);
        assertThat(res.getValue()).isEqualTo("LIC");
        assertThat(res.isMapped()).isFalse();
        assertThat(res.isAssigned()).isFalse();
        assertThat(res.isParsed()).isTrue();
        assertThat(res.isResolved()).isTrue();
    }

    @Test
    public void testTokenMapping_nullTokenArg()
    {
        Token nullToken = null;
        assertThrows(IllegalArgumentException.class, () -> {
            new InputValue(nullToken);
        });
    }

    @Test
    public void testInputValue_tokenTypeAndAssignedValueArg()
    {
        InputValue res = new InputValue(TokenType.CITY_NAME,"QUEENS");
        assertThat(res.getOriginalValue()).isEqualTo("QUEENS");
        assertThat(res.getTokenType()).isEqualTo(TokenType.CITY_NAME);
        assertThat(res.getValue()).isEqualTo("QUEENS");
        assertThat(res.isMapped()).isFalse();
        assertThat(res.isAssigned()).isTrue();
        assertThat(res.isParsed()).isFalse();
        assertThat(res.isResolved()).isTrue();
    }

    @Test
    public void testTokenMapping_nullTokenTypeAndAssignedValueArg()
    {
        TokenType nullTokenType = null;
        assertThrows(IllegalArgumentException.class, () -> {
            new InputValue(nullTokenType,"duh");
        });
    }

    @Test
    public void testTokenMapping_tokenTypeAndNullAssignedValueArg()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            new InputValue(TokenType.BIN,null);
        });
    }
}

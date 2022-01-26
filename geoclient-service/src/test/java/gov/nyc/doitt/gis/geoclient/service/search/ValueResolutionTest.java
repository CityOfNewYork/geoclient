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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

public class ValueResolutionTest {

    @Test
    public void testAdd() {
        ValueResolution res = new ValueResolution();
        assertThat(res.totalCount()).isEqualTo(0);
        assertThat(res.resolvedCount()).isEqualTo(0);
        assertThat(res.unresolvedCount()).isEqualTo(0);
        assertTrue(res.resolved().isEmpty());
        InputValue inputResolved = new InputValue(TokenType.AND, "and");
        assertTrue(inputResolved.isResolved());
        InputValue inputUnresolved = new InputValue(new Token(TokenType.BETWEEN, "bet", 0, 3), null);
        assertFalse(inputUnresolved.isResolved());
        res.add(inputResolved);
        assertThat(res.totalCount()).isEqualTo(1);
        assertThat(res.resolvedCount()).isEqualTo(1);
        assertThat(res.unresolvedCount()).isEqualTo(0);
        assertTrue(res.resolved().contains(inputResolved));
        res.add(inputUnresolved);
        assertThat(res.totalCount()).isEqualTo(2);
        assertThat(res.resolvedCount()).isEqualTo(1);
        assertThat(res.unresolvedCount()).isEqualTo(1);
        assertTrue(res.resolved().contains(inputResolved));
        assertFalse(res.resolved().contains(inputUnresolved));
        assertThat(res.resolvedValue(0)).isSameAs(inputResolved);
        assertThat(res.unresolvedValue(0)).isSameAs(inputUnresolved);
    }

}

/*
 * Copyright 2013-2016 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.service.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class BoroughTest
{

    @Test
    public void testParseInt()
    {
        assertThat(Borough.parseInt("1"), equalTo(1));
        assertThat(Borough.parseInt("2"), equalTo(2));
        assertThat(Borough.parseInt("3"), equalTo(3));
        assertThat(Borough.parseInt("4"), equalTo(4));
        assertThat(Borough.parseInt("5"), equalTo(5));
        assertThat(Borough.parseInt("12"), equalTo(12));
        assertThat(Borough.parseInt("-78"), equalTo(-78));
        assertThat(Borough.parseInt("Manhattan"), equalTo(1));
        assertThat(Borough.parseInt("MN"), equalTo(1));
        assertThat(Borough.parseInt("BRONX"), equalTo(2));
        assertThat(Borough.parseInt("bx"), equalTo(2));
        assertThat(Borough.parseInt("brooklyn"), equalTo(3));
        assertThat(Borough.parseInt("bk"), equalTo(3));
        assertThat(Borough.parseInt("qUeeNs"), equalTo(4));
        assertThat(Borough.parseInt("qN"), equalTo(4));
        assertThat(Borough.parseInt("staten island"), equalTo(5));
        assertThat(Borough.parseInt("STATEN IS"), equalTo(5));
        assertThat(Borough.parseInt("si"), equalTo(5));
        assertThat(Borough.parseInt("man"), equalTo(0));
    }

}

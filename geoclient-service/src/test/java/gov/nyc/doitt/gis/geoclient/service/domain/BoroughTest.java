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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class BoroughTest {

	@Test
	public void testParseInt() {
		assertThat(Borough.parseInt("1")).isEqualTo(1);
		assertThat(Borough.parseInt("2")).isEqualTo(2);
		assertThat(Borough.parseInt("3")).isEqualTo(3);
		assertThat(Borough.parseInt("4")).isEqualTo(4);
		assertThat(Borough.parseInt("5")).isEqualTo(5);
		assertThat(Borough.parseInt("12")).isEqualTo(12);
		assertThat(Borough.parseInt("-78")).isEqualTo(-78);
		assertThat(Borough.parseInt("Manhattan")).isEqualTo(1);
		assertThat(Borough.parseInt("MN")).isEqualTo(1);
		assertThat(Borough.parseInt("BRONX")).isEqualTo(2);
		assertThat(Borough.parseInt("bx")).isEqualTo(2);
		assertThat(Borough.parseInt("brooklyn")).isEqualTo(3);
		assertThat(Borough.parseInt("bk")).isEqualTo(3);
		assertThat(Borough.parseInt("qUeeNs")).isEqualTo(4);
		assertThat(Borough.parseInt("qN")).isEqualTo(4);
		assertThat(Borough.parseInt("staten island")).isEqualTo(5);
		assertThat(Borough.parseInt("STATEN IS")).isEqualTo(5);
		assertThat(Borough.parseInt("si")).isEqualTo(5);
		assertThat(Borough.parseInt("man")).isEqualTo(0);
	}

}

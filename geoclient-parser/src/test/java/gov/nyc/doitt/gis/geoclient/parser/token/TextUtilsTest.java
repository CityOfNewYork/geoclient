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
package gov.nyc.doitt.gis.geoclient.parser.token;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class TextUtilsTest {

    @Test
    public void testSanitize() {
        assertThat(TextUtils.sanitize(null)).isNull();
        assertThat(TextUtils.sanitize("")).isEqualTo("");
        assertThat(TextUtils.sanitize("abc")).isEqualTo("abc");
        assertThat(TextUtils.sanitize("abc def")).isEqualTo("abc def");
        assertThat(TextUtils.sanitize("  abc")).isEqualTo("abc");
        assertThat(TextUtils.sanitize("abc ")).isEqualTo("abc");
        assertThat(TextUtils.sanitize("  abc def  ")).isEqualTo("abc def");
        assertThat(TextUtils.sanitize("  abc   def  ")).isEqualTo("abc def");
        assertThat(TextUtils.sanitize(",  abc,   def  ,")).isEqualTo("abc, def");
        assertThat(TextUtils.sanitize(" ,  abc,   def  ,")).isEqualTo("abc, def");
        assertThat(TextUtils.sanitize(" ,.?!&^#$%@ abc-d &  def  ,?:+-")).isEqualTo("abc-d & def");
        assertThat(TextUtils.sanitize("abc&def")).isEqualTo("abc & def");
        assertThat(TextUtils.sanitize("abc && def")).isEqualTo("abc && def");
        assertThat(TextUtils.sanitize("abc&&def")).isEqualTo("abc && def");
        assertThat(TextUtils.sanitize("N.Y.")).isEqualTo("N.Y.");
        assertThat(TextUtils.sanitize("abc\tde\tf")).isEqualTo("abc de f");
    }

}

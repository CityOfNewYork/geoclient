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
package gov.nyc.doitt.gis.geoclient.parser.token;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import org.junit.Test;

public class TextUtilsTest
{

	@Test
	public void testSanitize()
	{
		assertThat(TextUtils.sanitize(null), is(nullValue()));
		assertThat(TextUtils.sanitize(""), is(equalTo("")));
		assertThat(TextUtils.sanitize("abc"), is(equalTo("abc")));
		assertThat(TextUtils.sanitize("abc def"), is(equalTo("abc def")));
		assertThat(TextUtils.sanitize("  abc"), is(equalTo("abc")));
		assertThat(TextUtils.sanitize("abc "), is(equalTo("abc")));
		assertThat(TextUtils.sanitize("  abc def  "), is(equalTo("abc def")));
		assertThat(TextUtils.sanitize("  abc   def  "), is(equalTo("abc def")));
		assertThat(TextUtils.sanitize(",  abc,   def  ,"), is(equalTo("abc, def")));
		assertThat(TextUtils.sanitize(" ,  abc,   def  ,"), is(equalTo("abc, def")));
		assertThat(TextUtils.sanitize(" ,.?!&^#$%@ abc-d &  def  ,?:+-"), is(equalTo("abc-d & def")));
		assertThat(TextUtils.sanitize("abc&def"), is(equalTo("abc & def")));
		assertThat(TextUtils.sanitize("abc && def"), is(equalTo("abc && def")));
		assertThat(TextUtils.sanitize("abc&&def"), is(equalTo("abc && def")));
		assertThat(TextUtils.sanitize("N.Y."), is(equalTo("N.Y.")));
		assertThat(TextUtils.sanitize("abc\tde\tf"), is(equalTo("abc de f")));
	}

}

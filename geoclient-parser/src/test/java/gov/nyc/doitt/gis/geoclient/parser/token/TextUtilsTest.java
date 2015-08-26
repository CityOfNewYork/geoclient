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

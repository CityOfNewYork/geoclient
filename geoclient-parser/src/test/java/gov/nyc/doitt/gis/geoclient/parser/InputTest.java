package gov.nyc.doitt.gis.geoclient.parser;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class InputTest
{

	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_idNull()
	{
		new Input(null,"value");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_idEmpty()
	{
		new Input("","value");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_valueNull()
	{
		new Input("id",null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_valueEmpty()
	{
		new Input("id","");
	}

	@Test
	public void testConstructor()
	{
		String id = "123";
		String value = " $abc";
		Input input = new Input(id, value);
		assertThat(input.getId(), is(id));
		assertThat(input.getValue(), is("abc"));
		assertThat(input.getUnsanitizedValue(), is(value));
	}

}

package gov.nyc.doitt.gis.geoclient.parser.regex;

import static org.junit.Assert.*;
import gov.nyc.doitt.gis.geoclient.parser.regex.PatternUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PatternUtilsTest
{

	@Test(expected=IllegalArgumentException.class)
	public void testLiteralMatchGroup_null()
	{
		PatternUtils.literalMatchGroup(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testLiteralMatchGroup_empty()
	{
		PatternUtils.literalMatchGroup(new ArrayList<String>());
	}
	@Test
	public void testLiteralMatchGroup()
	{
		List<String> list = new ArrayList<String>();
		list.add("cat");
		assertEquals("(cat)", PatternUtils.literalMatchGroup(list));
		list.add("dog");
		assertEquals("(cat|dog)", PatternUtils.literalMatchGroup(list));
		list.add("rat");
		assertEquals("(cat|dog|rat)", PatternUtils.literalMatchGroup(list));
	}
	
}

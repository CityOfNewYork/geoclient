package gov.nyc.doitt.gis.geoclient.doc;

import static org.junit.Assert.*;

import org.junit.Test;

public class GroupMemberTest
{

	@Test
	public void testMatches()
	{
		String id = "abc";
		GroupMember gm = new GroupMember();
		assertFalse(gm.matches(id));
		gm.setId(id);
		assertTrue(gm.matches(id  + "10"));
		assertFalse(gm.matches("10" + id));
	}

}

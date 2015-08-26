package gov.nyc.doitt.gis.geoclient.doc;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GroupDocumentationTest
{
	private ItemDocumentation itemDocumentation;
	
	@Before
	public void setUp()
	{
		this.itemDocumentation = new ItemDocumentation();
		this.itemDocumentation.setId("abc123");
	}

	@Test(expected=NullPointerException.class)
	public void testIsMember_groupMembersNull()
	{
		new GroupDocumentation().isMember(itemDocumentation);
	}

	@Test
	public void testIsMember()
	{
		GroupDocumentation groupDocumentation = new GroupDocumentation();
		List<GroupMember> members = new ArrayList<GroupMember>();
		groupDocumentation.setGroupMembers(members);
		assertFalse(groupDocumentation.isMember(itemDocumentation));
		GroupMember member = new GroupMember();
		member.setId(this.itemDocumentation.getId().substring(0,3));
		members.add(member);
		assertTrue(groupDocumentation.isMember(itemDocumentation));
	}

}

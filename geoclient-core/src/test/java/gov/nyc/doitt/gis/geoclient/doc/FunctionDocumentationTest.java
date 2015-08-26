package gov.nyc.doitt.gis.geoclient.doc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class FunctionDocumentationTest
{
	private List<GroupDocumentation> listOfGroups;
	private SortedSet<ItemDocumentation> sortedSetOfItemDocs;
	private ItemDocumentation iDoc1;
	private ItemDocumentation iDoc2;
	private GroupDocumentation group;

	@Before
	public void setUp()
	{
		sortedSetOfItemDocs = new TreeSet<ItemDocumentation>();
		listOfGroups = new ArrayList<GroupDocumentation>();
		iDoc1 = new ItemDocumentation();
		iDoc1.setId("abc123");
		iDoc2 = new ItemDocumentation();
		iDoc2.setId("xyz123");
		group = new GroupDocumentation();
		List<GroupMember> membersOnly = new ArrayList<GroupMember>();
		GroupMember m = new GroupMember();
		m.setId("xyz");
		membersOnly.add(m);
		group.setGroupMembers(membersOnly);
		assertTrue(group.isMember(iDoc2));
		assertFalse(group.isMember(iDoc1));
		this.listOfGroups.add(group);
	}

	@Test
	public void testIsGroupMember()
	{
		FunctionDocumentation fd = new FunctionDocumentation();
		assertFalse(fd.isGroupMember(iDoc2));
		assertFalse(fd.isGroupMember(iDoc1));
		fd.setGroups(listOfGroups);
		assertTrue(fd.isGroupMember(iDoc2));
		assertFalse(fd.isGroupMember(iDoc1));
	}
	
	@Test
	public void testAdd()
	{
		FunctionDocumentation fd = new FunctionDocumentation();
		fd.setGroups(listOfGroups);
		// Should not be added since it's a group member
		assertFalse(fd.add(iDoc2));
		// Should be added since it's NOT a group member
		assertTrue(fd.add(iDoc1));
		assertEquals(1, fd.getFields().size());
		assertTrue(fd.getFields().contains(iDoc1));
	}
	
	@Test
	public void testSetFields()
	{
		FunctionDocumentation fd = new FunctionDocumentation();
		fd.setGroups(listOfGroups);
		this.sortedSetOfItemDocs.add(iDoc1);
		this.sortedSetOfItemDocs.add(iDoc2);
		fd.setFields(sortedSetOfItemDocs);
		assertEquals(1, fd.getFields().size());
		assertTrue(fd.getFields().contains(iDoc1));
	}
	
	@Test
	public void testGetFieldsIsSortedByDisplayName()
	{
		FunctionDocumentation fd = new FunctionDocumentation();
		iDoc2.setDisplayName("aaa");
		this.sortedSetOfItemDocs.add(iDoc1);
		this.sortedSetOfItemDocs.add(iDoc2);
		assertEquals(2, this.sortedSetOfItemDocs.size());
		// ItemDocumentation.compareTo based on id so sortedSetOfItemDocs
		// uses that for sorting
		assertEquals(iDoc1, this.sortedSetOfItemDocs.first());
		assertEquals(iDoc2, this.sortedSetOfItemDocs.last());
		fd.setFields(sortedSetOfItemDocs);
		assertEquals(2, fd.getFields().size());
		// FunctionDocumentation uses inner class DisplayNameComparator to sort 
		// based on ItemDocumentation.displayName
		assertEquals(iDoc2, fd.getFields().first());
		assertEquals(iDoc1, fd.getFields().last());
	}
	
	@Test
	public void testDefaultConstructor()
	{
		FunctionDocumentation fd = new FunctionDocumentation();
		assertNull(fd.getDescription());
		assertNull(fd.getDisplayName());
		assertNull(fd.getFields());
		assertNull(fd.getId());
	}

	@Test
	public void testHasDisplayName()
	{
		FunctionDocumentation fd = new FunctionDocumentation();
		assertFalse(fd.hasDisplayName());
		fd.setDisplayName("xxx");
		assertTrue(fd.hasDisplayName());
	}	
}

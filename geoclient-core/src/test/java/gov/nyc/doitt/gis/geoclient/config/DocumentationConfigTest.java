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
package gov.nyc.doitt.gis.geoclient.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gov.nyc.doitt.gis.geoclient.doc.DataDictionary;
import gov.nyc.doitt.gis.geoclient.doc.FunctionDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.GroupDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.GroupMember;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.MissingDocumentation;
import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DocumentationConfigTest
{
	private DataDictionary dataDictionary;
	private Function functionMock;
	private FunctionDocumentation functionDocumentation;
	private DocumentationConfig documentationConfig;
	private WorkArea workAreaOne;
	private WorkArea workAreaTwo;
	private Field fieldOne;
	private Field fieldTwo;
	private Field fieldThree;
	private Field fieldFour;
	private Field groupedField;
	private ItemDocumentation iDocOne;
	private ItemDocumentation iDocTwo;
	private ItemDocumentation iDocFour;

	@Before
	public void setUp() throws Exception
	{
		this.functionMock = Mockito.mock(Function.class);
		this.fieldOne = new Field("fieldOne",0,12);
		this.fieldTwo = new Field("fieldTwo",12,10);
		this.fieldThree = new Field("fieldThree",22,2);
		this.fieldFour = new Field("fieldFour",24,6);
		this.groupedField = new Field("grouper1",30,2);
		GroupMember member = new GroupMember();
		member.setId("grouper");
		GroupDocumentation groupDoc = new GroupDocumentation();
		groupDoc.setGroupMembers(Arrays.asList(member));
		this.functionDocumentation = new FunctionDocumentation();
		this.functionDocumentation.setGroups(Arrays.asList(groupDoc));		
		assertTrue(this.functionDocumentation.isGroupMember(new MissingDocumentation(groupedField.getId())));
		SortedSet<Field> workAreaOneFields = new TreeSet<Field>();
		workAreaOneFields.add(fieldOne);
		workAreaOneFields.add(fieldTwo);
		this.workAreaOne = new WorkArea("WA1", workAreaOneFields);
		SortedSet<Field> workAreaTwoFields = new TreeSet<Field>();
		workAreaTwoFields.add(fieldThree);
		workAreaTwoFields.add(fieldFour);
		workAreaTwoFields.add(groupedField);
		this.workAreaTwo = new WorkArea("WA2", workAreaTwoFields);
		List<ItemDocumentation> items = new ArrayList<ItemDocumentation>();
		iDocOne = makeItemDocumentation(fieldOne);
		items.add(iDocOne);
		iDocTwo = makeItemDocumentation(fieldTwo);
		items.add(iDocTwo);
		iDocFour = makeItemDocumentation(fieldFour);
		items.add(iDocFour);
		this.dataDictionary = new DataDictionary(items);
		this.documentationConfig = new DocumentationConfig(dataDictionary);
	}
	
	@Test
	public void testDocumentTwoWorkAreaFunction()
	{
		Mockito.when(this.functionMock.getWorkAreaOne()).thenReturn(workAreaOne);
		Mockito.when(this.functionMock.isTwoWorkAreas()).thenReturn(true);
		Mockito.when(this.functionMock.getWorkAreaTwo()).thenReturn(workAreaTwo);
		FunctionDocumentation result = this.documentationConfig.document(functionDocumentation, functionMock);
		assertSame(functionDocumentation, result);
		assertEquals(4, result.getFields().size());
		// grouped field should have been skipped
		assertFalse(result.getFields().contains(new MissingDocumentation(this.groupedField.getId())));
		assertTrue(result.getFields().remove(iDocOne));
		assertTrue(result.getFields().remove(iDocTwo));
		assertTrue(result.getFields().remove(iDocFour));
		assertEquals(1, result.getFields().size());
		assertTrue(result.getFields().first() instanceof MissingDocumentation);		
	}
	
	@Test
	public void testDocumentOneWorkAreaFunction()
	{
		Mockito.when(this.functionMock.getWorkAreaOne()).thenReturn(workAreaOne);
		Mockito.when(this.functionMock.isTwoWorkAreas()).thenReturn(false);
		FunctionDocumentation result = this.documentationConfig.document(functionDocumentation, functionMock);
		assertSame(functionDocumentation, result);
		assertEquals(2, result.getFields().size());
		assertTrue(result.getFields().contains(iDocOne));
		assertTrue(result.getFields().contains(iDocTwo));
		Mockito.verify(this.functionMock, Mockito.never()).getWorkAreaTwo();
	}
	
	private ItemDocumentation makeItemDocumentation(Field field)
	{
		ItemDocumentation iDoc = new ItemDocumentation();
		iDoc.setId(field.getId());
		return iDoc;
	}
}

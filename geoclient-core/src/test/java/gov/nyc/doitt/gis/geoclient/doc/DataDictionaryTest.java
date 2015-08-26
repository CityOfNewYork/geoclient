package gov.nyc.doitt.gis.geoclient.doc;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DataDictionaryTest
{
	private ItemDocumentation itemDocumentation;
	private List<ItemDocumentation> items;
	private List<String> aliases;
	private DataDictionary dataDictionary;

	@Before
	public void setUp() throws Exception
	{
		this.aliases = new ArrayList<String>();
		this.aliases.add("hippo");
		this.aliases.add("froyo");
		this.itemDocumentation = new ItemDocumentation("cujo");
		this.items = new ArrayList<ItemDocumentation>();
		this.dataDictionary = new DataDictionary(items);
	}

	@Test
	public void testConstructor()
	{
		assertSame(this.items, this.dataDictionary.getItems());
	}

	@Test
	public void testFindItem()
	{
		assertNull(this.dataDictionary.findItem(itemDocumentation.getId()));
		this.items.add(itemDocumentation);
		assertSame(this.itemDocumentation, this.dataDictionary.findItem(itemDocumentation.getId()));
		assertNull(this.dataDictionary.findItem(this.aliases.get(0)));
		this.itemDocumentation.setAliases(aliases);
		assertSame(this.itemDocumentation, this.dataDictionary.findItem(this.aliases.get(0)));
	}

}

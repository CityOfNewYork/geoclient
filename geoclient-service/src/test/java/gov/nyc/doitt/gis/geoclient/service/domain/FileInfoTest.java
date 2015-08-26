package gov.nyc.doitt.gis.geoclient.service.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FileInfoTest
{
	private FileInfo fileInfo;

	@Before
	public void setUp() throws Exception
	{
		this.fileInfo = new FileInfo();
	}

	@Test
	public void testGetFormattedDate()
	{
		String dateString = "021112";
		this.fileInfo.setDate(dateString);
		assertEquals(this.fileInfo.applyFormat(dateString),this.fileInfo.getFormattedDate());
	}

	@Test
	public void testApplyFormat()
	{
		assertNull(this.fileInfo.applyFormat(null));
		String badDate = "bad";
		assertEquals(badDate,this.fileInfo.applyFormat(badDate));
		assertEquals("2014-01-21",this.fileInfo.applyFormat("140121"));
	}

}

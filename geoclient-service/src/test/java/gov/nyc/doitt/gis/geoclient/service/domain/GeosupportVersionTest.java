package gov.nyc.doitt.gis.geoclient.service.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GeosupportVersionTest
{

	private GeosupportVersion geosupportVersion;
	private List<FileInfo> fileInfoList;
	
	@Before
	public void setUp() throws Exception
	{
		this.geosupportVersion = new GeosupportVersion();
		this.fileInfoList = new ArrayList<>();
	}
	
	private void addTag(String tag, String release)
	{
		FileInfo f = new FileInfo();
		f.setTag(tag);
		f.setRelease(release);
		this.fileInfoList.add(f);
		this.geosupportVersion.setGeoFileInfo(fileInfoList);
	}

	@Test
	public void testGetVersion()
	{
		assertEquals(GeosupportVersion.VERSION_NOT_AVAILABLE,this.geosupportVersion.getVersion());
		addTag(GeosupportVersion.GEO_FILE_TAG, "0132");
		assertEquals("13.2", geosupportVersion.getVersion());
	}

	@Test
	public void testGetRelease()
	{
		assertEquals(GeosupportVersion.RELEASE_NOT_AVAILABLE,this.geosupportVersion.getRelease());
		addTag(GeosupportVersion.PAD_FILE_TAG, "13X");
		assertEquals("13X", geosupportVersion.getRelease());
	}

	@Test
	public void testFormatVersion()
	{
		assertEquals(GeosupportVersion.VERSION_NOT_AVAILABLE, geosupportVersion.formatVersion(""));
		assertEquals(GeosupportVersion.VERSION_NOT_AVAILABLE, geosupportVersion.formatVersion("0000"));
		assertEquals("1", geosupportVersion.formatVersion("00001"));
		assertEquals("32", geosupportVersion.formatVersion("0032"));
		assertEquals("13.2", geosupportVersion.formatVersion("0132"));
		assertEquals("13.24", geosupportVersion.formatVersion("1324"));
	}

	@Test
	public void testFormatRelease()
	{
		assertEquals("13X", geosupportVersion.formatRelease("13X"));
	}

}

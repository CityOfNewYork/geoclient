/*
 * Copyright 2013-2019 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.service.domain;

import java.util.List;

public class GeosupportVersion
{
	public static final String GEO_FILE_TAG = "GEO";
	public static final String PAD_FILE_TAG = "PAD";
	public static final String RELEASE_NOT_AVAILABLE = "RELEASE_NOT_AVAILABLE";
	public static final String VERSION_NOT_AVAILABLE = "VERSION_NOT_AVAILABLE";

	private List<FileInfo> geoFileInfo;
	private ThinFileInfo thinFileInfo;
	private FileInfo grid1RFileInfo;
	private FileInfo auxsegFileInfo;
	private FileInfo tpadFileInfo;
	private FileInfo apequivFileInfo;
	private List<String> dsNames;

	public GeosupportVersion()
	{

	}

	public String getVersion()
	{
		FileInfo f = findGeoFileInfo(GEO_FILE_TAG);
		if (f != null && f.getRelease() != null)
		{
			return formatVersion(f.getRelease());
		}
		return VERSION_NOT_AVAILABLE;
	}

	public String getRelease()
	{
		FileInfo f = findGeoFileInfo(PAD_FILE_TAG);
		if (f != null && f.getRelease() != null)
		{
			return formatRelease(f.getRelease());
		}
		return RELEASE_NOT_AVAILABLE;
	}

	String formatVersion(String version)
	{
		String v = version.trim();
		// Remove leading zeros
		while (v.startsWith("0"))
		{
			v = v.substring(1);
		}
		int len = v.length();
		switch (len)
		{
		case 0:
			return VERSION_NOT_AVAILABLE;
		case 3:
		case 4:
			v = v.substring(0, 2) + "." + v.substring(2);
			break;
		default:
			// It is 1 or 2 characters, so do nothing
			break;
		}
		return v;
	}

	String formatRelease(String version)
	{
		return version;
	}

	private FileInfo findGeoFileInfo(String tag)
	{
		if (this.geoFileInfo != null)
		{
			for (FileInfo fileInfo : this.geoFileInfo)
			{
				if (tag.equals(fileInfo.getTag()))
				{
					return fileInfo;
				}
			}
		}
		return null;
	}

	public List<FileInfo> getGeoFileInfo()
	{
		return geoFileInfo;
	}

	public ThinFileInfo getThinFileInfo()
	{
		return thinFileInfo;
	}

	public FileInfo getGrid1RFileInfo()
	{
		return grid1RFileInfo;
	}

	public FileInfo getAuxsegFileInfo()
	{
		return auxsegFileInfo;
	}

	public FileInfo getTpadFileInfo()
	{
		return tpadFileInfo;
	}

	public FileInfo getApequivFileInfo()
	{
		return apequivFileInfo;
	}

	public void setGeoFileInfo(List<FileInfo> geoFileInfo)
	{
		this.geoFileInfo = geoFileInfo;
	}

	public void setThinFileInfo(ThinFileInfo thinFileInfo)
	{
		this.thinFileInfo = thinFileInfo;
	}

	public void setGrid1RFileInfo(FileInfo grid1rFileInfo)
	{
		grid1RFileInfo = grid1rFileInfo;
	}

	public void setAuxsegFileInfo(FileInfo auxsegFileInfo)
	{
		this.auxsegFileInfo = auxsegFileInfo;
	}

	public void setTpadFileInfo(FileInfo tpadFileInfo)
	{
		this.tpadFileInfo = tpadFileInfo;
	}

	public void setApequivFileInfo(FileInfo apequivFileInfo)
	{
		this.apequivFileInfo = apequivFileInfo;
	}

	public List<String> getDsNames()
	{
		return dsNames;
	}

	public void setDsNames(List<String> dsNames)
	{
		this.dsNames = dsNames;
	}

	@Override
	public String toString()
	{
		return "GeosupportVersion [geoFileInfo=" + geoFileInfo + ", thinFileInfo=" + thinFileInfo + ", grid1RFileInfo="
				+ grid1RFileInfo + ", auxsegFileInfo=" + auxsegFileInfo + ", tpadFileInfo=" + tpadFileInfo
				+ ", apequivFileInfo=" + apequivFileInfo + ", dsNames=" + dsNames + "]";
	}

}

/*
 * Copyright 2013-2022 the original author or authors.
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

/**
 * Bean class for data returned by Geosupport function HR.
 */
public class GeosupportVersion {
    public static final String GEO_FILE_TAG = "GEO";
    public static final String PAD_FILE_TAG = "PAD";
    public static final String RELEASE_NOT_AVAILABLE = "RELEASE_NOT_AVAILABLE";
    public static final String VERSION_NOT_AVAILABLE = "VERSION_NOT_AVAILABLE";

    private List<String> dsNames;
    private List<FileInfo> geoFileInfo;
    private ThinFileInfo thinFileInfo;
    private FileInfo apFileInfo;
    private FileInfo apequivFileInfo;
    private FileInfo auxsegFileInfo;
    private FileInfo grid1RFileInfo;
    private FileInfo sneqFileInfo;
    private FileInfo stat1AFileInfo;
    private FileInfo stat1FileInfo;
    private FileInfo stat2FileInfo;
    private FileInfo stat3FileInfo;
    private FileInfo stat3SFileInfo;
    private FileInfo statAPFileInfo;
    private FileInfo statBLFileInfo;
    private FileInfo statBNFileInfo;
    private FileInfo statDFileInfo;
    private FileInfo statFileInfo;
    private FileInfo tpadFileInfo;
    private FileInfo upadFileInfo;
    private String geofilesDirectory;

    public String getGeofilesDirectory() {
        return geofilesDirectory;
    }

    public void setGeofilesDirectory(String geofilesDirectory) {
        this.geofilesDirectory = geofilesDirectory;
    }

    public GeosupportVersion() {

    }

    public String getVersion() {
        FileInfo f = findGeoFileInfo(GEO_FILE_TAG);
        if (f != null && f.getRelease() != null) {
            return formatVersion(f.getRelease());
        }
        return VERSION_NOT_AVAILABLE;
    }

    public String getRelease() {
        FileInfo f = findGeoFileInfo(PAD_FILE_TAG);
        if (f != null && f.getRelease() != null) {
            return formatRelease(f.getRelease());
        }
        return RELEASE_NOT_AVAILABLE;
    }

    String formatVersion(String version) {

        String v = version.trim();
        // Remove trailing zeros
        while (v.endsWith("0")) {
            v = v.substring(0, v.length() - 1);
        }
        // Remove leading zeros
        while (v.startsWith("0")) {
            v = v.substring(1);
        }
        int len = v.length();
        switch (len) {
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

    String formatRelease(String version) {
        return version;
    }

    private FileInfo findGeoFileInfo(String tag) {
        if (this.geoFileInfo != null) {
            for (FileInfo fileInfo : this.geoFileInfo) {
                if (tag.equals(fileInfo.getTag())) {
                    return fileInfo;
                }
            }
        }
        return null;
    }

    public List<FileInfo> getGeoFileInfo() {
        return geoFileInfo;
    }

    public ThinFileInfo getThinFileInfo() {
        return thinFileInfo;
    }

    public FileInfo getGrid1RFileInfo() {
        return grid1RFileInfo;
    }

    public FileInfo getAuxsegFileInfo() {
        return auxsegFileInfo;
    }

    public FileInfo getTpadFileInfo() {
        return tpadFileInfo;
    }

    public FileInfo getApequivFileInfo() {
        return apequivFileInfo;
    }

    public void setGeoFileInfo(List<FileInfo> geoFileInfo) {
        this.geoFileInfo = geoFileInfo;
    }

    public void setThinFileInfo(ThinFileInfo thinFileInfo) {
        this.thinFileInfo = thinFileInfo;
    }

    public void setGrid1RFileInfo(FileInfo grid1rFileInfo) {
        grid1RFileInfo = grid1rFileInfo;
    }

    public void setAuxsegFileInfo(FileInfo auxsegFileInfo) {
        this.auxsegFileInfo = auxsegFileInfo;
    }

    public void setTpadFileInfo(FileInfo tpadFileInfo) {
        this.tpadFileInfo = tpadFileInfo;
    }

    public void setApequivFileInfo(FileInfo apequivFileInfo) {
        this.apequivFileInfo = apequivFileInfo;
    }

    public FileInfo getApFileInfo() {
        return apFileInfo;
    }

    public void setApFileInfo(FileInfo apFileInfo) {
        this.apFileInfo = apFileInfo;
    }

    public FileInfo getUpadFileInfo() {
        return upadFileInfo;
    }

    public void setUpadFileInfo(FileInfo upadFileInfo) {
        this.upadFileInfo = upadFileInfo;
    }

    public FileInfo getStatFileInfo() {
        return statFileInfo;
    }

    public void setStatFileInfo(FileInfo statFileInfo) {
        this.statFileInfo = statFileInfo;
    }

    public FileInfo getStat1FileInfo() {
        return stat1FileInfo;
    }

    public void setStat1FileInfo(FileInfo stat1FileInfo) {
        this.stat1FileInfo = stat1FileInfo;
    }

    public FileInfo getStat1AFileInfo() {
        return stat1AFileInfo;
    }

    public void setStat1AFileInfo(FileInfo stat1aFileInfo) {
        stat1AFileInfo = stat1aFileInfo;
    }

    public FileInfo getStat2FileInfo() {
        return stat2FileInfo;
    }

    public void setStat2FileInfo(FileInfo stat2FileInfo) {
        this.stat2FileInfo = stat2FileInfo;
    }

    public FileInfo getStat3FileInfo() {
        return stat3FileInfo;
    }

    public void setStat3FileInfo(FileInfo stat3FileInfo) {
        this.stat3FileInfo = stat3FileInfo;
    }

    public FileInfo getStat3SFileInfo() {
        return stat3SFileInfo;
    }

    public void setStat3SFileInfo(FileInfo stat3sFileInfo) {
        stat3SFileInfo = stat3sFileInfo;
    }

    public FileInfo getStatAPFileInfo() {
        return statAPFileInfo;
    }

    public void setStatAPFileInfo(FileInfo statAPFileInfo) {
        this.statAPFileInfo = statAPFileInfo;
    }

    public FileInfo getStatBLFileInfo() {
        return statBLFileInfo;
    }

    public void setStatBLFileInfo(FileInfo statBLFileInfo) {
        this.statBLFileInfo = statBLFileInfo;
    }

    public FileInfo getStatBNFileInfo() {
        return statBNFileInfo;
    }

    public void setStatBNFileInfo(FileInfo statBNFileInfo) {
        this.statBNFileInfo = statBNFileInfo;
    }

    public FileInfo getStatDFileInfo() {
        return statDFileInfo;
    }

    public void setStatDFileInfo(FileInfo statDFileInfo) {
        this.statDFileInfo = statDFileInfo;
    }

    public FileInfo getSneqFileInfo() {
        return sneqFileInfo;
    }

    public void setSneqFileInfo(FileInfo sneqFileInfo) {
        this.sneqFileInfo = sneqFileInfo;
    }

    public List<String> getDsNames() {
        return dsNames;
    }

    public void setDsNames(List<String> dsNames) {
        this.dsNames = dsNames;
    }

    @Override
    public String toString() {
        return "GeosupportVersion [apFileInfo=" + apFileInfo + ", apequivFileInfo=" + apequivFileInfo
                + ", auxsegFileInfo=" + auxsegFileInfo + ", dsNames=" + dsNames + ", geofilesDirectory="
                + geofilesDirectory + ", geoFileInfo=" + geoFileInfo
                + ", grid1RFileInfo=" + grid1RFileInfo + ", sneqFileInfo=" + sneqFileInfo + ", stat1AFileInfo="
                + stat1AFileInfo + ", stat1FileInfo=" + stat1FileInfo + ", stat2FileInfo=" + stat2FileInfo
                + ", stat3FileInfo=" + stat3FileInfo + ", stat3SFileInfo=" + stat3SFileInfo + ", statAPFileInfo="
                + statAPFileInfo + ", statBLFileInfo=" + statBLFileInfo + ", statBNFileInfo=" + statBNFileInfo
                + ", statDFileInfo=" + statDFileInfo + ", statFileInfo=" + statFileInfo + ", thinFileInfo="
                + thinFileInfo + ", tpadFileInfo=" + tpadFileInfo + ", upadFileInfo=" + upadFileInfo + "]";
    }

}

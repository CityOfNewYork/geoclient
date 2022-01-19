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
package gov.nyc.doitt.gis.geoclient.service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gov.nyc.doitt.gis.geoclient.service.domain.FileInfo;
import gov.nyc.doitt.gis.geoclient.service.domain.GeosupportVersion;
import gov.nyc.doitt.gis.geoclient.service.domain.ThinFileInfo;

public class GeosupportVersionMapper extends AbstractParameterMapper<GeosupportVersion> {

    @Override
    public GeosupportVersion fromParameters(Map<String, Object> source, GeosupportVersion destination)
            throws MappingException {
        destination.setGeoFileInfo(toGeoFileInfo(source));
        destination.setThinFileInfo(toThinFileInfo(source));
        destination.setGrid1RFileInfo(toFileInfo(source, "grid1R", ""));
        destination.setAuxsegFileInfo(toFileInfo(source, "auxseg", ""));
        destination.setTpadFileInfo(toFileInfo(source, "tpad", ""));
        destination.setApequivFileInfo(toFileInfo(source, "apequiv", ""));
        destination.setApFileInfo(toFileInfo(source, "ap", ""));
        destination.setUpadFileInfo(toFileInfo(source, "upad", ""));
        destination.setStatFileInfo(toFileInfo(source, "stat", ""));
        destination.setStat1FileInfo(toFileInfo(source, "stat1", ""));
        destination.setStat1AFileInfo(toFileInfo(source, "stat1A", ""));
        destination.setStat2FileInfo(toFileInfo(source, "stat2", ""));
        destination.setStat3FileInfo(toFileInfo(source, "stat3", ""));
        destination.setStat3SFileInfo(toFileInfo(source, "stat3S", ""));
        destination.setApFileInfo(toFileInfo(source, "statAP", ""));
        destination.setStatBLFileInfo(toFileInfo(source, "statBL", ""));
        destination.setStatBNFileInfo(toFileInfo(source, "statBN", ""));
        destination.setStatDFileInfo(toFileInfo(source, "statD", ""));
        destination.setSneqFileInfo(toFileInfo(source, "sneq", ""));
        destination.setDsNames(toDsNames(source));
        destination.setGeofilesDirectory(get("geofilesDirectory", source));
        return destination;
    }

    @Override
    public Map<String, Object> toParameters(GeosupportVersion source, Map<String, Object> destination)
            throws MappingException {
        // TODO Implement me
        return destination;
    }

    protected List<String> toDsNames(Map<String, Object> source) {
        List<String> dsNames = new ArrayList<>();
        for (int i = 1; i < 27; i++) {
            dsNames.add(get("dsName" + i, source));
        }
        return dsNames;
    }

    protected List<FileInfo> toGeoFileInfo(Map<String, Object> source) {
        List<FileInfo> geoFileInfoList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            geoFileInfoList.add(toFileInfo(source, "geo", String.valueOf(i)));
        }
        return geoFileInfoList;
    }

    protected ThinFileInfo toThinFileInfo(Map<String, Object> source) {
        List<String> recordTypes = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            String recordType = get("thinFileRecordType" + i, source);
            if(recordType !=null){
                recordTypes.add(recordType);
            }
        }
        String tag = get("thinFileTag", source);
        String date = get("thinFileDate", source);
        String release = get("thinFileRelease", source);
        String recordCount = get("thinFileRecordCount", source);
        return new ThinFileInfo(recordTypes, tag, date, release, recordCount, null);
    }

    protected FileInfo toFileInfo(Map<String, Object> source, String prefix, String suffix) {
        if (suffix == null) {
            suffix = "";
        }
        String recordType = get(String.format("%sFileRecordType%s", prefix, suffix), source);
        String tag = get(String.format("%sFileTag%s", prefix, suffix), source);
        String date = get(String.format("%sFileDate%s", prefix, suffix), source);
        String release = get(String.format("%sFileRelease%s", prefix, suffix), source);
        String recordCount = get(String.format("%sFileRecordCount%s", prefix, suffix), source);
        return new FileInfo(recordType, tag, date, release, recordCount);
    }

    private String get(String name, Map<String, Object> map){
       if(map.containsKey(name)){
           Object value = map.get(name);
           if(value != null){
               return value.toString();
           }
       } 
       return null;
    }
}

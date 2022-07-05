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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import gov.nyc.doitt.gis.geoclient.service.domain.FileInfo;
import gov.nyc.doitt.gis.geoclient.service.domain.GeosupportVersion;
import gov.nyc.doitt.gis.geoclient.service.domain.ThinFileInfo;

public class GeosupportVersionMapper extends AbstractParameterMapper<GeosupportVersion> {

    static final String[] FILE_INFO_OBJ_PROPERTY_NAMES = new String[]{
        "Tag",
        "Date",
        "RecordType",
        "RecordCount"
    };
    static final String[] SIMPLE_FIELD_NAMES = new String[] {
        "geofilesDirectory"
    };
    static final String[] SCALAR_FILE_FIELD_PREFIXES = new String[] {
        "ap",
        "apequiv",
        "auxseg",
        "grid1R",
        "sneq",
        "stat",
        "stat1",
        "stat1A",
        "stat2",
        "stat3",
        "stat3S",
        "statAP",
        "statBL",
        "statBN",
        "statD",
        "tpad",
        "upad"
    };
    static final int DSNAMES_NBR = 26;
    static final int GEO_FILES_NBR = 9;
    static final int THIN_FILE_RECORD_TYPES_NBR = 3;

    @Override
    public GeosupportVersion fromParameters(Map<String, Object> source, GeosupportVersion destination)
            throws MappingException {
        // Special case handling
        destination.setGeoFileInfo(toGeoFileInfo(source));
        destination.setThinFileInfo(toThinFileInfo(source));
        destination.setDsNames(toDsNames(source));
        // Simple (scalar) FileInfo fields
        destination.setApFileInfo(toFileInfo(source, "ap", ""));
        destination.setApequivFileInfo(toFileInfo(source, "apequiv", ""));
        destination.setAuxsegFileInfo(toFileInfo(source, "auxseg", ""));
        destination.setGrid1RFileInfo(toFileInfo(source, "grid1R", ""));
        destination.setSneqFileInfo(toFileInfo(source, "sneq", ""));
        destination.setStat1AFileInfo(toFileInfo(source, "stat1A", ""));
        destination.setStat1FileInfo(toFileInfo(source, "stat1", ""));
        destination.setStat2FileInfo(toFileInfo(source, "stat2", ""));
        destination.setStat3FileInfo(toFileInfo(source, "stat3", ""));
        destination.setStat3SFileInfo(toFileInfo(source, "stat3S", ""));
        destination.setStatAPFileInfo(toFileInfo(source, "statAP", ""));
        destination.setStatBLFileInfo(toFileInfo(source, "statBL", ""));
        destination.setStatBNFileInfo(toFileInfo(source, "statBN", ""));
        destination.setStatDFileInfo(toFileInfo(source, "statD", ""));
        destination.setStatFileInfo(toFileInfo(source, "stat", ""));
        destination.setTpadFileInfo(toFileInfo(source, "tpad", ""));
        destination.setUpadFileInfo(toFileInfo(source, "upad", ""));
        // Simple (scalar) fields
        destination.setGeofilesDirectory(get("geofilesDirectory", source));
        return destination;
    }

    @Override
    public Map<String, Object> toParameters(GeosupportVersion source, Map<String, Object> destination)
            throws MappingException {
        throw new MappingException("Method toParameters not supported by this Mapper.");
    }

    protected List<String> toDsNames(Map<String, Object> source) {
        List<String> dsNames = new ArrayList<>();
        // 1-based loop
        for (int i = 1; i < (DSNAMES_NBR + 1); i++) {
            dsNames.add(get(String.format("dsName%d", i), source));
        }
        return dsNames;
    }

    protected List<FileInfo> toGeoFileInfo(Map<String, Object> source) {
        List<FileInfo> geoFileInfoList = new ArrayList<>();
        // 1-based loop
        for (int i = 1; i < (GEO_FILES_NBR + 1); i++) {
            geoFileInfoList.add(toFileInfo(source, "geo", String.valueOf(i)));
        }
        return geoFileInfoList;
    }

    protected ThinFileInfo toThinFileInfo(Map<String, Object> source) {
        List<String> recordTypes = new ArrayList<>();
        // 1-based loop
        for (int i = 1; i < (THIN_FILE_RECORD_TYPES_NBR + 1); i++) {
            String recordType = get(String.format("thinFileRecordType%d", i), source);
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

    protected FileInfo toFileInfo(Map<String, Object> source, String prefix) {
        return toFileInfo(source, prefix, "");
    }

    protected FileInfo toFileInfo(Map<String, Object> source, String prefix, String suffix) {
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

    List<String> dsNameFieldNames() {
        return IntStream.rangeClosed(1, DSNAMES_NBR).mapToObj(
            (element) -> String.format("dsName%d", element)).collect(Collectors.toList());
    }

    List<String> simpleFileFieldNames(){
        List<String> props = Arrays.asList(FILE_INFO_OBJ_PROPERTY_NAMES);
        List<String> fields = Arrays.asList(SCALAR_FILE_FIELD_PREFIXES);
        List<String> results = new ArrayList<>();
        fields.forEach(f -> props.forEach(p -> results.add(String.format("%sFile%s", f, p))));
        return results;
    }

    List<String> geoFileFieldNames() {
        Stream<String> props = Stream.of(FILE_INFO_OBJ_PROPERTY_NAMES);
        IntStream nbr = IntStream.rangeClosed(1, GEO_FILES_NBR);
        return props.flatMap(name -> nbr.mapToObj(i -> String.format("geoFile%s%d", name, i))).collect(Collectors.toList());
    }
}

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

import static gov.nyc.doitt.gis.geoclient.service.mapper.GeosupportVersionMapper.DSNAMES_NBR;
import static gov.nyc.doitt.gis.geoclient.service.mapper.GeosupportVersionMapper.FILE_INFO_OBJ_PROPERTY_NAMES;
import static gov.nyc.doitt.gis.geoclient.service.mapper.GeosupportVersionMapper.SCALAR_FILE_FIELD_PREFIXES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.service.domain.GeosupportVersion;

/**
 * Tests for GeosupportVersionMapper.
 *
 * @author Matthew Lipper
 * @since 2.0
 * @see Mapper
 */
public class GeosupportVersionMapperTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeosupportVersionMapperTest.class);

    private GeosupportVersionMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new GeosupportVersionMapper();
    }

    @Test
    void testJava() {
        LOGGER.info("Hello, world.");
        List<String> list = Arrays.asList("abc1", "abc2", "abc3");
        Optional<String> stream = list.stream().filter(element -> {
            LOGGER.info("filter() was called");
            return element.contains("2");
        }).map(element -> {
            LOGGER.info("map() was called");
            return element.toUpperCase();
        }).findFirst();
        assertNotNull(stream);
    }

    @Test
    void testDsNameFieldNames() {
        List<String> results = mapper.dsNameFieldNames();
        assertEquals(DSNAMES_NBR, results.size());
        for (int i = 1; i <= DSNAMES_NBR; i++) {
            assertTrue(results.contains(String.format("dsName%d", i)));
        }
    }

    @Test
    void testSimpleFileFieldNames() {
        List<String> results = mapper.simpleFileFieldNames();
        int expectedNumber = SCALAR_FILE_FIELD_PREFIXES.length * FILE_INFO_OBJ_PROPERTY_NAMES.length;
        assertEquals(expectedNumber, results.size());
        for (int i = 0; i < SCALAR_FILE_FIELD_PREFIXES.length; i++) {
            String prefix = SCALAR_FILE_FIELD_PREFIXES[i];
            for (int j = 0; j < FILE_INFO_OBJ_PROPERTY_NAMES.length; j++) {
                String prop = FILE_INFO_OBJ_PROPERTY_NAMES[j];
                assertTrue(results.contains(String.format("%sFile%s", prefix, prop)));
            }
        }
    }

    @Test
    void testFromParameters() {
        GeosupportVersion dest = new GeosupportVersion();
        Map<String, Object> src = functionHRResponse();
        GeosupportVersion result = mapper.fromParameters(src, dest);
        assertNotNull(result);
        assertSame(dest, result);
    }

    @Test
    void testToParameters() {
    }

    private Map<String, Object> functionHRResponse() {
        Map<String, Object> results = new HashMap<>();
        results.put("apFileDate", "211014");
        results.put("apFileRecordCount", "00965728");
        results.put("apFileRecordType", "0000");
        results.put("apFileRelease", "21D");
        results.put("apFileTag", "AP");
        results.put("apequivFileDate", "170717");
        results.put("apequivFileRecordCount", "00000001");
        results.put("apequivFileRecordType", "0000");
        results.put("apequivFileRelease", "17C");
        results.put("apequivFileTag", "APEQ");
        results.put("auxSegFileDate", "211020");
        results.put("auxSegFileRecordCount", "00039478");
        results.put("auxSegFileRecordType", "0000");
        results.put("auxSegFileRelease", "21D");
        results.put("auxSegFileTag", "SAUX");
        results.put("dsName1", "edequiv");
        results.put("dsName10", "GRID1R");
        results.put("dsName11", "AUXSEG");
        results.put("dsName12", "TPAD");
        results.put("dsName13", "apequiv");
        results.put("dsName14", "AP");
        results.put("dsName15", "UPAD");
        results.put("dsName16", "STAT");
        results.put("dsName17", "STT1");
        results.put("dsName18", "STT1A");
        results.put("dsName19", "STT2");
        results.put("dsName2", "GRID1");
        results.put("dsName20", "STT3");
        results.put("dsName21", "STT3S");
        results.put("dsName22", "STTAP");
        results.put("dsName23", "STTBL");
        results.put("dsName24", "STTBN");
        results.put("dsName25", "STTD");
        results.put("dsName26", "snequiv");
        results.put("dsName3", "GRID2");
        results.put("dsName4", "GRID3");
        results.put("dsName5", "PAD");
        results.put("dsName6", "san");
        results.put("dsName7", "snd");
        results.put("dsName8", "STRETCH");
        results.put("dsName9", "thined");
        results.put("geoFileDate1", "210930");
        results.put("geoFileDate2", "200221");
        results.put("geoFileDate3", "211021");
        results.put("geoFileDate4", "211020");
        results.put("geoFileDate5", "211020");
        results.put("geoFileDate6", "211021");
        results.put("geoFileDate7", "211021");
        results.put("geoFileDate8", "211018");
        results.put("geoFileDate9", "211020");
        results.put("geoFileRecordCount1", "00000001");
        results.put("geoFileRecordCount2", "00000001");
        results.put("geoFileRecordCount3", "00230813");
        results.put("geoFileRecordCount4", "00077382");
        results.put("geoFileRecordCount5", "00213709");
        results.put("geoFileRecordCount6", "01282926");
        results.put("geoFileRecordCount7", "00000750");
        results.put("geoFileRecordCount8", "00108872");
        results.put("geoFileRecordCount9", "00028029");
        results.put("geoFileRecordType1", "0000");
        results.put("geoFileRecordType2", "0000");
        results.put("geoFileRecordType3", "0000");
        results.put("geoFileRecordType4", "0000");
        results.put("geoFileRecordType5", "0000");
        results.put("geoFileRecordType6", "0000");
        results.put("geoFileRecordType7", "0000");
        results.put("geoFileRecordType8", "0000");
        results.put("geoFileRecordType9", "0000");
        results.put("geoFileRelease1", "2140");
        results.put("geoFileRelease2", "20A");
        results.put("geoFileRelease3", "21D");
        results.put("geoFileRelease4", "21D");
        results.put("geoFileRelease5", "21D");
        results.put("geoFileRelease6", "21D");
        results.put("geoFileRelease7", "21D");
        results.put("geoFileRelease8", "21D");
        results.put("geoFileRelease9", "21D");
        results.put("geoFileTag1", "GEO");
        results.put("geoFileTag2", "EDEQ");
        results.put("geoFileTag3", "GRD1");
        results.put("geoFileTag4", "GRD2");
        results.put("geoFileTag5", "GRD3");
        results.put("geoFileTag6", "PAD");
        results.put("geoFileTag7", "SAN");
        results.put("geoFileTag8", "SND");
        results.put("geoFileTag9", "STCH");
        results.put("geofilesDirectory", "/opt/geosupport/current/fls/");
        results.put("geosupportFunctionCode", "HR");
        results.put("geosupportReturnCode", "00");
        results.put("grid1RFileDate", "211021");
        results.put("grid1RFileRecordCount", "00230765");
        results.put("grid1RFileRecordType", "0000");
        results.put("grid1RFileRelease", "21D");
        results.put("grid1RFileTag", "GR1R");
        results.put("sneqFileDate", "211022");
        results.put("sneqFileRecordCount", "00000840");
        results.put("sneqFileRecordType", "0000");
        results.put("sneqFileRelease", "21D");
        results.put("sneqFileTag", "SNEQ");
        results.put("stat1AFileDate", "******");
        results.put("stat1AFileRecordCount", "********");
        results.put("stat1AFileRecordType", "STT1");
        results.put("stat1AFileRelease", "****");
        results.put("stat1AFileTag", "A***");
        results.put("stat1FileDate", "******");
        results.put("stat1FileRecordCount", "********");
        results.put("stat1FileRecordType", "STT1");
        results.put("stat1FileRelease", "****");
        results.put("stat1FileTag", "****");
        results.put("stat2FileDate", "******");
        results.put("stat2FileRecordCount", "********");
        results.put("stat2FileRecordType", "STT2");
        results.put("stat2FileRelease", "****");
        results.put("stat2FileTag", "****");
        results.put("stat3FileDate", "******");
        results.put("stat3FileRecordCount", "********");
        results.put("stat3FileRecordType", "STT3");
        results.put("stat3FileRelease", "****");
        results.put("stat3FileTag", "****");
        results.put("stat3SFileDate", "******");
        results.put("stat3SFileRecordCount", "********");
        results.put("stat3SFileRecordType", "STT3");
        results.put("stat3SFileRelease", "****");
        results.put("stat3SFileTag", "S***");
        results.put("statAPFileDate", "******");
        results.put("statAPFileRecordCount", "********");
        results.put("statAPFileRecordType", "STTA");
        results.put("statAPFileRelease", "****");
        results.put("statAPFileTag", "P***");
        results.put("statBLFileDate", "******");
        results.put("statBLFileRecordCount", "********");
        results.put("statBLFileRecordType", "STTB");
        results.put("statBLFileRelease", "****");
        results.put("statBLFileTag", "L***");
        results.put("statBNFileDate", "******");
        results.put("statBNFileRecordCount", "********");
        results.put("statBNFileRecordType", "STTB");
        results.put("statBNFileRelease", "****");
        results.put("statBNFileTag", "N***");
        results.put("statDFileDate", "******");
        results.put("statDFileRecordCount", "********");
        results.put("statDFileRecordType", "STTD");
        results.put("statDFileRelease", "****");
        results.put("statDFileTag", "****");
        results.put("statFileDate", "******");
        results.put("statFileRecordCount", "********");
        results.put("statFileRecordType", "STAT");
        results.put("statFileRelease", "****");
        results.put("statFileTag", "****");
        results.put("thinFileDate", "170515");
        results.put("thinFileRecordCount", "00005904");
        results.put("thinFileRecordType1", "0000");
        results.put("thinFileRecordType2", "0001");
        results.put("thinFileRecordType3", "0002");
        results.put("thinFileRelease", "17B");
        results.put("thinFileTag", "THIN");
        results.put("tpadFileDate", "211021");
        results.put("tpadFileRecordCount", "00000000");
        results.put("tpadFileRecordType", "0000");
        results.put("tpadFileRelease", "21C");
        results.put("tpadFileTag", "TPAD");
        results.put("upadFileDate", "211021");
        results.put("upadFileRecordCount", "00000001");
        results.put("upadFileRecordType", "0000");
        results.put("upadFileRelease", "21D");
        results.put("upadFileTag", "UPAD");
        results.put("workAreaFormatIndicatorIn", "C");
        return results;
    }
}

package gov.nyc.doitt.gis.geoclient.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.service.search.GeosupportReturnCode;
import gov.nyc.doitt.gis.geoclient.service.search.ResponseStatus;

class ResponseStatusCustomConverterTest {

    private ResponseStatusCustomConverter converter;
    private GeosupportReturnCodeFixture grc1;
    private GeosupportReturnCodeFixture grc2;
    private List<String> similarNames;

    @BeforeEach
    void setUp() throws Exception {
        this.converter = new ResponseStatusCustomConverter();
        this.grc1 = new GeosupportReturnCodeFixture();
        this.grc2 = new GeosupportReturnCodeFixture(false);
        this.similarNames = new ArrayList<>();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.converter = null;
        this.grc1.reset();
        this.grc2.reset();
        this.similarNames.clear();
    }

    @Test
    void testConvertMapToResponseStatus_SuccessWithWarning() {
        MapWrapper wrapMap = MapWrapper.successWithWarning();
        Map<String, Object> map = wrapMap.getMap();
        ResponseStatus responseStatus = new ResponseStatus();
        this.converter.convert(responseStatus, map, responseStatus.getClass(), map.getClass());
        assertEquals(responseStatus.getGeosupportReturnCode().getReturnCode(), wrapMap.getReturnCode1());
        assertEquals(responseStatus.getGeosupportReturnCode().getReasonCode(), wrapMap.getReasonCode1());
        assertEquals(responseStatus.getGeosupportReturnCode().getMessage(), wrapMap.getMessage1());
        assertEquals(responseStatus.getGeosupportReturnCode2().getReturnCode(), wrapMap.getReturnCode2());
        assertEquals(responseStatus.getGeosupportReturnCode2().getReasonCode(), wrapMap.getReasonCode2());
        assertEquals(responseStatus.getGeosupportReturnCode2().getMessage(), wrapMap.getMessage2());
        assertTrue(responseStatus.getSimilarNames().isEmpty());
    }

    @Test
    void testConvertResponseStatusToMap_SuccessWithWarning() {

        ResponseStatus source = new ResponseStatus();
        Map<String, Object> destination = new HashMap<>();
        MapWrapper fixtureData = MapWrapper.successWithWarning();
        GeosupportReturnCode expectedGrc1 = resetAndPopulate(source, fixtureData, true);
        GeosupportReturnCode expectedGrc2 = resetAndPopulate(source, fixtureData, false);

        this.converter.convert(destination, source, destination.getClass(), source.getClass());
        // Wrap the actual Map<String, Object> for easy value lookup
        MapWrapper actualWrappedMap = new MapWrapper(destination);
        // GRC1
        assertEquals(expectedGrc1.getReturnCode(), actualWrappedMap.getReturnCode1());
        assertEquals(expectedGrc1.getReasonCode(), actualWrappedMap.getReasonCode1());
        assertEquals(expectedGrc1.getMessage(), actualWrappedMap.getMessage1());
        // GRC2
        assertEquals(expectedGrc2.getReturnCode(), actualWrappedMap.getReturnCode2());
        assertEquals(expectedGrc2.getReasonCode(), actualWrappedMap.getReasonCode2());
        assertEquals(expectedGrc2.getMessage(), actualWrappedMap.getMessage2());
    }

    private GeosupportReturnCode resetAndPopulate(ResponseStatus source, MapWrapper fixtureData, boolean isOne) {
        if (isOne) {
            this.grc1.resetAndPopulate(fixtureData); // populate rc1
            source.setGeosupportReturnCode(this.grc1.getGeosupportReturnCode());
            return source.getGeosupportReturnCode();
        }
        this.grc2.resetAndPopulate(fixtureData); // populate rc2
        source.setGeosupportReturnCode2(this.grc2.getGeosupportReturnCode());
        return source.getGeosupportReturnCode2();
    }
}

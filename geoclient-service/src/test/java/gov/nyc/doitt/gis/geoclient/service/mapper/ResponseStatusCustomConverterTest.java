package gov.nyc.doitt.gis.geoclient.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    // @Test
    // void testConvertResponseStatusToMap_SuccessWithWarning() {
    // MapWrapper wrapMap = MapWrapper.successWithWarning();
    // Map<String, Object> map = new HashMap<>(); //empty!
    // ResponseStatus responseStatus = new ResponseStatus();
    // responseStatus.setGeosupportReturnCode(grc1.);
    // this.converter.convert(map, responseStatus, map.getClass(),
    // responseStatus.getClass());
    // assertEquals(wrapMap.getReturnCode1(),
    // responseStatus.getGeosupportReturnCode().getReturnCode());
    // assertEquals(responseStatus.getGeosupportReturnCode().getReasonCode(),
    // wrapMap.getReasonCode1());
    // assertEquals(responseStatus.getGeosupportReturnCode().getMessage(),
    // wrapMap.getMessage1());
    // assertEquals(responseStatus.getGeosupportReturnCode2().getReturnCode(),
    // wrapMap.getReturnCode2());
    // assertEquals(responseStatus.getGeosupportReturnCode2().getReasonCode(),
    // wrapMap.getReasonCode2());
    // assertEquals(responseStatus.getGeosupportReturnCode2().getMessage(),
    // wrapMap.getMessage2());
    // }


}

package gov.nyc.doitt.gis.geoclient.api;

import static gov.nyc.doitt.gis.geoclient.api.InputParam.BOROUGH_CODE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.HOUSE_NUMBER;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_NAME;
import static gov.nyc.doitt.gis.geoclient.api.OutputParam.GEOSUPPORT_RETURN_CODE;
import static gov.nyc.doitt.gis.geoclient.api.OutputParam.GEOSUPPORT_RETURN_CODE2;
import static gov.nyc.doitt.gis.geoclient.api.ReturnCodeValue.SUCCESS;
import static gov.nyc.doitt.gis.geoclient.api.ReturnCodeValue.WARN;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.jni.GeoclientJni;
import gov.nyc.doitt.gis.geoclient.util.StringUtils;

public class GeoclientCoreIntegrationTest {

    private static GeosupportConfig geosupportConfig;

    @BeforeAll
    public static void beforeAll() throws Exception {
        geosupportConfig = new GeosupportConfig(new GeoclientJni());
    }

    @Test
    public void testFunctionAp() {
        Function function = geosupportConfig.getFunction(Function.FAP);
        Map<String, Object> parameters = new HashMap<>();
        parameters.putAll(function.getConfiguration().requiredArguments());
        parameters.put(InputParam.HOUSE_NUMBER, "59");
        parameters.put(InputParam.STREET_NAME, "Maiden Lane");
        parameters.put(InputParam.BOROUGH_CODE, "1");
        Map<String, Object> result = function.call(parameters);
        assertTrue(succeeded(GEOSUPPORT_RETURN_CODE, result));
    }

    @Test
    public void testFunction1B() {
        Function function = geosupportConfig.getFunction(Function.F1B);
        Map<String, Object> parameters = new HashMap<>();
        parameters.putAll(function.getConfiguration().requiredArguments());
        parameters.put(HOUSE_NUMBER, "280");
        parameters.put(STREET_NAME, "Riverside Drive");
        parameters.put(BOROUGH_CODE, "1");
        Map<String, Object> result = function.call(parameters);
        assertTrue(succeededWithWarning(GEOSUPPORT_RETURN_CODE, result)); // F1EX success with warning
        assertTrue(succeeded(GEOSUPPORT_RETURN_CODE2, result)); // F1AX success
    }

    private boolean succeeded(String grcName, Map<String, Object> result) {
        String grc = StringUtils.stringOrNullValueOf(result.get(grcName));
        return SUCCESS.is(grc);
    }

    private boolean succeededWithWarning(String grcName, Map<String, Object> result) {
        String grc = StringUtils.stringOrNullValueOf(result.get(grcName));
        return WARN.is(grc);
    }
}
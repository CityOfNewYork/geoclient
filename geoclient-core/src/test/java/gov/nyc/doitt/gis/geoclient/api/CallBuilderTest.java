package gov.nyc.doitt.gis.geoclient.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.function.Configuration;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;

class CallBuilderTest {

    private String requiredArgKey = "requiredArg";
    private String requiredArgValue = "value";
    private String functionCode = "XX";
    private String houseNumber = "2";
    private String streetName = "Metrotech Center";
    private String boroughName = "Brooklyn";
    private String boroughCode = "3";
    private String zipCode = "11201";

    @Test
    void testAddressCallBuilder() throws Exception {
        Function function = getFunction(functionCode);
        CallBuilder.Address location = CallBuilder.newInstance(function, CallBuilder.Address.class);
        location.houseNumber(houseNumber);
        location.streetName(streetName);
        location.boroughName(boroughName);
        location.zipCode(zipCode);
        Map<String, Object> result = location.arguments();
        assertEquals(functionCode, result.get(InputParam.GEOSUPPORT_FUNCTION_CODE));
        assertEquals(houseNumber, result.get(InputParam.HOUSE_NUMBER));
        assertEquals(streetName, result.get(InputParam.STREET_NAME));
        assertEquals(boroughCode, result.get(InputParam.BOROUGH_CODE));
        assertEquals(requiredArgValue, result.get(requiredArgKey));
    }

    private Function getFunction(String id) {
        return new Function() {

            @Override
            public boolean isTwoWorkAreas() {
                return false;
            }

            @Override
            public WorkArea getWorkAreaTwo() {
                return null;
            }

            @Override
            public WorkArea getWorkAreaOne() {
                return null;
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public Configuration getConfiguration() {
                return new Configuration() {
                    @Override
                    public Map<String, Object> requiredArguments() {
                        Map<String, Object> requiredArgs = new HashMap<>();
                        requiredArgs.put(InputParam.GEOSUPPORT_FUNCTION_CODE, id);
                        requiredArgs.put(requiredArgKey, requiredArgValue);
                        return requiredArgs;
                    }
                };
            }

            @Override
            public Map<String, Object> call(Map<String, Object> parameters) {
                return null;
            }
        };
    }
}

package gov.nyc.doitt.gis.geoclient.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.api.OutputParam;
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

    public static class ReturnCodeData {
        public final String SUCCESS_RETURN_CODE = "00";
        public final String SUCCESS_REASON_CODE = "";
        public final String SUCCESS_MESSAGE = "";
        public final String WARNING_RETURN_CODE = "01";
        public final String WARNING_REASON_CODE = "V";
        public final String WARNING_MESSAGE = "<normd_input_address_number> <normd_input_street_name> IS ON <LEFT|RIGHT> SIDE OF <true_street_name>";
        public final String ERROR_RETURN_CODE = "50";
        public final String ERROR_REASON_CODE = "2"; // Number (1-4) of similar names returned
        public final String ERROR_MESSAGE = "<input_street_name> IS AN INVALID STREET NAME FOR THIS LOCATION";

        interface Stateful {
            default boolean isTerminal() {
                return this.getClass().isAssignableFrom(Terminal.class);
            }

            default boolean isChange() {
                return this.getClass().isAssignableFrom(Change.class);
            }

            default boolean isNoOp() {
                return this.getClass().isAssignableFrom(NoOp.class);
            }
        }

        enum Change implements Stateful {
            SUCCESS, WARNING, ERROR
        }

        enum NoOp implements Stateful {
            WITH("_");
            private String value;

            private NoOp(String value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return this.value;
            }

        }

        enum Terminal implements Stateful {
            RETURN_CODE, REASON_CODE, MESSAGE
        }

        private Stack<Stateful> state;

        public ReturnCodeData() {
            this.state = new Stack<>();
        }

        public ReturnCodeData success() {
            this.state.push(Change.SUCCESS);
            return this;
        }

        public ReturnCodeData warning() {
            this.state.push(Change.WARNING);
            return this;
        }

        public ReturnCodeData error() {
            this.state.push(Change.ERROR);
            return this;
        }

        public ReturnCodeData with() {
            this.state.push(NoOp.WITH);
            return this;
        }

        public ReturnCodeData returnCode() {
            this.state.push(Terminal.RETURN_CODE);
            return this;
        }

        public ReturnCodeData reasonCode() {
            this.state.push(Terminal.REASON_CODE);
            return this;
        }

        public ReturnCodeData message() {
            this.state.push(Terminal.MESSAGE);
            return this;
        }

        public String value() {
            if (state.isEmpty() || state.size() != 3) {
                throw new IllegalStateException(String.format(
                        "Grammar requires state [Change, NoOp, Terminal] when value() called. Instead was %s",
                        state.toString()));
            }
            StringBuffer buffer = new StringBuffer();
            Terminal t = (Terminal) this.state.pop();
            buffer.append(t);
            NoOp n = (NoOp) this.state.pop();
            buffer.append(n);
            Change c = (Change) this.state.pop();
            buffer.append(c);
            this.state.clear();
            String constName = buffer.toString();
            return constName;
            // java.lang.reflect.Field field = this.getClass().getField(constName);
            // field.get

        }
    }

    public static class MapWrapper {
        private final Map<String, Object> map;

        public MapWrapper(Map<String, Object> map) {
            super();
            this.map = map;
        }

        public MapWrapper() {
            this(new HashMap<String, Object>());
        }

        public void reset() {
            this.map.clear();
        }

        public Map<String, Object> getMap() {
            return this.map;
        }

        public String getReturnCode1() {
            return stringOrNull(OutputParam.GEOSUPPORT_RETURN_CODE);
        }

        public String getReturnCode2() {
            return stringOrNull(OutputParam.GEOSUPPORT_RETURN_CODE2);
        }

        public String getReasonCode1() {
            return stringOrNull(OutputParam.REASON_CODE);
        }

        public String getReasonCode2() {
            return stringOrNull(OutputParam.REASON_CODE2);
        }

        public String getMessage1() {
            return stringOrNull(OutputParam.MESSAGE);
        }

        public String getMessage2() {
            return stringOrNull(OutputParam.MESSAGE2);
        }

        public MapWrapper setReturnCode1(String value) {
            this.map.put(OutputParam.GEOSUPPORT_RETURN_CODE, value);
            return this;
        }

        public MapWrapper setReturnCode2(String value) {
            this.map.put(OutputParam.GEOSUPPORT_RETURN_CODE2, value);
            return this;
        }

        public MapWrapper setReasonCode1(String value) {
            this.map.put(OutputParam.REASON_CODE, value);
            return this;
        }

        public MapWrapper setReasonCode2(String value) {
            this.map.put(OutputParam.REASON_CODE2, value);
            return this;
        }

        public MapWrapper setMessage1(String value) {
            this.map.put(OutputParam.MESSAGE, value);
            return this;
        }

        public MapWrapper setMessage2(String value) {
            this.map.put(OutputParam.MESSAGE2, value);
            return this;
        }

        private String stringOrNull(String key) {
            if (this.map.containsKey(key)) {
                return this.map.get(key).toString();
            }
            return null;
        }

        public static MapWrapper successWithWarning() {
            ReturnCodeData rdc = new ReturnCodeData();
            return MapWrapper.wrapMap(rdc.SUCCESS_RETURN_CODE, rdc.SUCCESS_REASON_CODE, rdc.SUCCESS_MESSAGE,
                    rdc.WARNING_RETURN_CODE, rdc.WARNING_REASON_CODE, rdc.WARNING_MESSAGE);
        }

        public static MapWrapper warningWithSuccess() {
            ReturnCodeData rdc = new ReturnCodeData();
            return MapWrapper.wrapMap(rdc.WARNING_RETURN_CODE, rdc.WARNING_REASON_CODE, rdc.WARNING_MESSAGE,
                    rdc.SUCCESS_RETURN_CODE, rdc.SUCCESS_REASON_CODE, rdc.SUCCESS_MESSAGE);
        }

        // Assumes one to six arguments in the following order:
        // String returnCode1, reasonCode1, message1, returnCode2, reasonCode2, message2
        public static MapWrapper wrapMap(String... strings) {
            if (strings == null || strings.length == 0) {
                throw new NullPointerException("This function requires 1 to 6 string arguments but none were given");
            }
            if (strings.length < 1 || strings.length > 6) {
                throw new NullPointerException(String
                        .format("This function requires 1 to 6 string arguments but %d were given", strings.length));
            }
            MapWrapper result = new MapWrapper(new HashMap<>());
            for (int i = 0; i < strings.length; i++) {
                String string = strings[i];
                int arg = i + 1;
                switch (arg) {
                case 1:
                    result.setReturnCode1(string);
                    break;
                case 2:
                    result.setReasonCode1(string);
                    break;
                case 3:
                    result.setMessage1(string);
                    break;
                case 4:
                    result.setReturnCode2(string);
                    break;
                case 5:
                    result.setReasonCode2(string);
                    break;
                case 6:
                    result.setMessage2(string);
                    break;

                default:
                    throw new IllegalStateException();
                }
            }
            return result;
        }
    }

    public static class GeosupportReturnCodeFixture {
        private final GeosupportReturnCode grc;
        private final boolean one;

        public GeosupportReturnCodeFixture(GeosupportReturnCode grc, boolean one) {
            super();
            this.grc = grc;
            this.one = one;
        }

        public GeosupportReturnCodeFixture(GeosupportReturnCode grc) {
            this(grc, true);
        }

        public GeosupportReturnCodeFixture(boolean isOne) {
            this(new GeosupportReturnCode(), isOne);
        }

        public GeosupportReturnCodeFixture() {
            this(new GeosupportReturnCode(), true);
        }

        public GeosupportReturnCode getGeosupportReturnCode() {
            return this.grc;
        }

        public void reset() {
            this.grc.setReturnCode(null);
            this.grc.setReasonCode(null);
            this.grc.setMessage(null);
        }

        public GeosupportReturnCodeFixture returnCode(String returnCode) {
            this.grc.setReturnCode(returnCode);
            return this;
        }

        public GeosupportReturnCodeFixture reasonCode(String reasonCode) {
            this.grc.setReasonCode(reasonCode);
            return this;
        }

        public GeosupportReturnCodeFixture message(String message) {
            this.grc.setMessage(message);
            return this;
        }

        public boolean isOne() {
            return one;
        }

        public boolean isTwo() {
            return !one;
        }

        // @formatter:off
        public static GeosupportReturnCodeFixture fromMap(Map<String, Object> map, boolean isRc1) {
            GeosupportReturnCodeFixture result = new GeosupportReturnCodeFixture(isRc1);
            MapWrapper mapWrap = new MapWrapper(map);
            if (isRc1) {
                result.returnCode(mapWrap.getReturnCode1())
                        .reasonCode(mapWrap.getReasonCode1())
                        .message(mapWrap.getMessage1());
            } else {
                result.returnCode(mapWrap.getReturnCode2())
                        .reasonCode(mapWrap.getReasonCode2())
                        .message(mapWrap.getMessage2());
            }
            return result;
        }
        // @formatter:on
    }

}

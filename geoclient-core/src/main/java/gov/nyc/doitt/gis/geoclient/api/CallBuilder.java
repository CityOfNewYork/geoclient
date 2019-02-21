package gov.nyc.doitt.gis.geoclient.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import gov.nyc.doitt.gis.geoclient.config.InputParam;
import gov.nyc.doitt.gis.geoclient.function.Function;

public class CallBuilder {

    private static class Builder {
        private final Map<String, Object> params = new HashMap<>();

        Object arg(String name, Object value) {
            return this.params.put(name, value);
        }

        void args(Map<String, Object> args) {
            this.params.putAll(args);
        }

        Map<String, Object> arguments() {
            return this.params;
        }
    }

    protected static abstract class Location {
        protected final Builder builder = new Builder();

        Location(Function function) {
            this.builder.args(function.getConfiguration().requiredArguments());
        }

        public Map<String, Object> arguments() {
            return this.builder.arguments();
        }

        void addBoroughCode(String value) {
            if (value == null) {
                throw new NullPointerException("Borough code argument cannot be null");
            }
            this.builder.arg(InputParam.BOROUGH_CODE, value);
        }

        void addBoroughName(String value) {
            if (value == null) {
                throw new NullPointerException("Borough name argument cannot be null");
            }
            if ("manhattan".equalsIgnoreCase(value)) {
                addBoroughCode("1");
            } else if ("bronx".equalsIgnoreCase(value)) {
                addBoroughCode("2");
            } else if ("brooklyn".equalsIgnoreCase(value)) {
                addBoroughCode("3");
            } else if ("queens".equalsIgnoreCase(value)) {
                addBoroughCode("4");
            } else if ("staten island".equalsIgnoreCase(value)) {
                addBoroughCode("5");
            } else {
                throw new IllegalArgumentException(String.format("Unrecognized borough {}", value));
            }
        }

    }

    public static class Address extends Location {
        public Address(Function function) {
            super(function);
        }

        Address houseNumber(String value) {
            this.builder.arg(InputParam.HOUSE_NUMBER, value);
            return this;
        }

        Address streetName(String value) {
            this.builder.arg(InputParam.STREET_NAME, value);
            return this;
        }

        Address streetCode(String value) {
            this.builder.arg(InputParam.STREET_CODE, value);
            return this;
        }

        Address boroughCode(String value) {
            addBoroughCode(value);
            return this;
        }

        Address boroughName(String value) {
            addBoroughName(value);
            return this;
        }

        Address zipCode(String value) {
            this.builder.arg(InputParam.ZIP_CODE, value);
            return this;
        }
    }

    public static <T extends Location> T newInstance(Function function, Class<T> callType) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException, IllegalArgumentException, InstantiationException {
        Constructor<T> ctor = callType.getConstructor(Function.class);
        return ctor.newInstance(function);
    }

}

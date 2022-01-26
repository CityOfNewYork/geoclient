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
package gov.nyc.doitt.gis.geoclient.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import gov.nyc.doitt.gis.geoclient.function.Function;

public class CallBuilder {

    private static class NumberedParamNames {
        private Map<Integer, String> mapping;

        NumberedParamNames(String... paramNames) {
            this.mapping = new HashMap<>(paramNames.length);
            for (int i = 0; i < paramNames.length; i++) {
                this.mapping.put(i, paramNames[i]);
            }
        }

        String nth(int ordinal) {
            return this.mapping.get(ordinal - 1);
        }
    }

    private static class Arguments {
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

    protected static abstract class Builder {
        protected final Arguments arguments = new Arguments();
        protected final NumberedParamNames boroughCodeParams = new NumberedParamNames(InputParam.BOROUGH_CODE,
                InputParam.BOROUGH_CODE2, InputParam.BOROUGH_CODE3);
        protected final NumberedParamNames streetCodeParams = new NumberedParamNames(InputParam.STREET_CODE,
                InputParam.STREET_CODE2, InputParam.STREET_CODE3);
        protected final NumberedParamNames streetNameParams = new NumberedParamNames(InputParam.STREET_NAME,
                InputParam.STREET_NAME2, InputParam.STREET_NAME3);

        Builder(Function function) {
            this.arguments.args(function.getConfiguration().requiredArguments());
        }

        public Map<String, Object> arguments() {
            return this.arguments.arguments();
        }

        public Builder boroughNameOne(String boroughName) {
            addBoroughName(boroughName, 1);
            return this;
        }

        public Builder boroughNameTwo(String boroughName) {
            addBoroughName(boroughName, 2);
            return this;
        }

        public Builder boroughNameThree(String boroughName) {
            addBoroughName(boroughName, 3);
            return this;
        }

        private void addBoroughName(String name, int number) {
            if (name == null) {
                throw new NullPointerException("Borough name argument cannot be null");
            }
            Borough borough = null;
            if ((borough = Boroughs.fromName(name)) == null) {
                throw new IllegalArgumentException(String.format("Unrecognized borough named '{}'", name));
            }
            addBorough(borough, number);
        }

        public Builder boroughOne(Borough borough) {
            addBorough(borough, 1);
            return this;
        }

        public Builder boroughTwo(Borough borough) {
            addBorough(borough, 2);
            return this;
        }

        public Builder boroughThree(Borough borough) {
            addBorough(borough, 3);
            return this;
        }

        private void addBorough(Borough borough, int number) {
            if (borough == null) {
                throw new NullPointerException("Borough argument cannot be null");
            }
            addBoroughCode(borough.getCode(), number);
        }

        public Builder boroughCodeOne(String boroughCode) {
            addBoroughCode(boroughCode, 1);
            return this;
        }

        public Builder boroughCodeTwo(String boroughCode) {
            addBoroughCode(boroughCode, 2);
            return this;
        }

        public Builder boroughCodeThree(String boroughCode) {
            addBoroughCode(boroughCode, 3);
            return this;
        }

        private void addBoroughCode(String value, int number) {
            if (value == null) {
                throw new NullPointerException("Borough code argument cannot be null");
            }
            this.arguments.arg(this.boroughCodeParams.nth(number), value);
        }

        public Builder streetOne(Street street) {
            addStreet(street, 1);
            return this;
        }

        public Builder streetTwo(Street street) {
            addStreet(street, 2);
            return this;
        }

        public Builder streetThree(Street street) {
            addStreet(street, 3);
            return this;
        }

        private void addStreet(Street street, int number) {
            if (street == null) {
                throw new NullPointerException("Street argument cannot be null");
            }
            if (street.getName() != null) {
                // Prefer using street name, if available
                addStreetName(street.getName(), number);
            } else if (street.getCode() != null) {
                addStreetCode(street.getCode(), number);
            } else {
                throw new IllegalArgumentException("Street argument's code and name fields cannot both be null");
            }
        }

        public Builder streetNameOne(String streetName) {
            addStreetName(streetName, 1);
            return this;
        }

        public Builder streetNameTwo(String streetName) {
            addStreetName(streetName, 2);
            return this;
        }

        public Builder streetNameThree(String streetName) {
            addStreetName(streetName, 3);
            return this;
        }

        private void addStreetName(String streetName, int number) {
            this.arguments.arg(this.streetNameParams.nth(number), streetName);
        }

        public Builder streetCodeOne(String streetCode) {
            addStreetCode(streetCode, 1);
            return this;
        }

        public Builder streetCodeTwo(String streetCode) {
            addStreetCode(streetCode, 2);
            return this;
        }

        public Builder streetCodeThree(String streetCode) {
            addStreetCode(streetCode, 3);
            return this;
        }

        private void addStreetCode(String streetCode, int number) {
            this.arguments.arg(this.streetCodeParams.nth(number), streetCode);
        }
    }

    public static class Address extends Builder {
        public Address(Function function) {
            super(function);
        }

        Address street(Street street) {
            return (Address) this.streetOne(street);
        }

        Address streetCode(String code) {
            return (Address) this.streetCodeOne(code);
        }

        Address streetName(String name) {
            return (Address) this.streetNameOne(name);
        }

        Address borough(Borough borough) {
            return (Address) this.boroughOne(borough);
        }

        Address boroughCode(String code) {
            return (Address) this.boroughCodeOne(code);
        }

        Address boroughName(String name) {
            return (Address) this.boroughNameOne(name);
        }

        Address houseNumber(String value) {
            this.arguments.arg(InputParam.HOUSE_NUMBER, value);
            return this;
        }

        Address zipCode(String value) {
            this.arguments.arg(InputParam.ZIP_CODE, value);
            return this;
        }
    }

    public static <T extends Builder> T newInstance(Function function, Class<T> callType) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException, IllegalArgumentException, InstantiationException {
        Constructor<T> ctor = callType.getConstructor(Function.class);
        return ctor.newInstance(function);
    }

}

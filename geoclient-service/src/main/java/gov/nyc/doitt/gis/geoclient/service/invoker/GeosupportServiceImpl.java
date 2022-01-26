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
package gov.nyc.doitt.gis.geoclient.service.invoker;

import static gov.nyc.doitt.gis.geoclient.api.InputParam.BBL_BOROUGH_CODE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.BBL_TAX_BLOCK;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.BBL_TAX_LOT;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.BIN;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.BOROUGH_CODE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.BOROUGH_CODE2;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.BOROUGH_CODE3;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.COMPASS_DIRECTION;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.GEOSUPPORT_FUNCTION_CODE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.HOUSE_NUMBER;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.NORMALIZATION_FORMAT;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.NORMALIZATION_FORMAT_COMPACT_VALUE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.NORMALIZATION_FORMAT_SORT_VALUE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.NORMALIZATION_LENGTH;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_CODE;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_CODE2;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_CODE3;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_NAME;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_NAME2;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_NAME3;
import static gov.nyc.doitt.gis.geoclient.api.InputParam.ZIP_CODE;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.api.Boroughs;
import gov.nyc.doitt.gis.geoclient.api.InvalidStreetCodeException;
import gov.nyc.doitt.gis.geoclient.api.StreetCode;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.service.configuration.AppConfig;
import gov.nyc.doitt.gis.geoclient.service.domain.Version;
import gov.nyc.doitt.gis.geoclient.util.Assert;

/**
 * Default implementation for making Geoclient calls to Geosupport.
 *
 * @author mlipper
 *
 */
public class GeosupportServiceImpl implements GeosupportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeosupportServiceImpl.class);

    /**
     * Callback for configuring calls.
     *
     * @author mlipper
     *
     */
    private abstract class Call {
        private final Function function;
        private final FieldSetConverter latLongConverter;

        public Call(Function function, FieldSetConverter latLongFieldSetConverter) {
            super();
            this.function = function;
            this.latLongConverter = latLongFieldSetConverter;
        }

        public Map<String, Object> execute() {
            // Arguments required by function for every call
            Map<String, Object> requiredArguments = requiredArguments(function);
            LOGGER.debug("{} requiredArguments {}", id(), requiredArguments);

            // Arguments given by the user
            Map<String, Object> userArguments = userArguments();
            LOGGER.debug("{} userArguments {}", id(), userArguments);

            // Combined arguments passed to Geosupport function call
            Map<String, Object> allArguments = new HashMap<String, Object>();
            allArguments.putAll(requiredArguments);
            allArguments.putAll(userArguments);
            LOGGER.debug("{} allArguments {}", id(), allArguments);

            // Call Geosupport function
            Map<String, Object> result = function.call(allArguments);
            LOGGER.debug("{} result {}", id(), result);

            // Add lat/long info
            latLongConverter.convert(id(), result);
            LOGGER.debug("{} result post lat/lon enhancement {}", id(), result);

            return result;
        }

        @Override
        public String toString() {
            return id();
        }

        private String id() {
            return "F" + this.function.getId();
        }

        private Map<String, Object> requiredArguments(Function function) {
            return function.getConfiguration().requiredArguments();
        }

        protected Map<String, Object> newMap() {
            return new TreeMap<String, Object>();
        }

        protected abstract Map<String, Object> userArguments();
    }

    private AppConfig serviceConfiguration;

    public GeosupportServiceImpl(AppConfig serviceConfiguration) {
        super();
        this.serviceConfiguration = serviceConfiguration;
    }

    @Override
    public Map<String, Object> callFunctionAP(String houseNumber, String street, String borough, String zip) {

        return new Call(serviceConfiguration.functionAP(), serviceConfiguration.latLongFieldSetConverter()) {
            @Override
            public Map<String, Object> userArguments() {
            Map<String, Object> params = newMap();
            if (houseNumber != null) {
                params.put(HOUSE_NUMBER, houseNumber);
            }

            params.put(STREET_NAME, street);

            if (borough != null) {
                params.put(BOROUGH_CODE, Boroughs.parseInt(borough));
            }

            if (zip != null) {
                params.put(ZIP_CODE, zip);
            }
            return params;
            }
        }.execute();
    }

    @Override
    public Map<String, Object> callFunction1B(final String houseNumber, final String street, final String borough,
            final String zip) {
        return new Call(serviceConfiguration.function1B(), serviceConfiguration.latLongFieldSetConverter()) {
            @Override
            public Map<String, Object> userArguments() {
                Map<String, Object> params = newMap();
                if (houseNumber != null) {
                    params.put(HOUSE_NUMBER, houseNumber);
                }

                params.put(STREET_NAME, street);

                if (borough != null) {
                    params.put(BOROUGH_CODE, Boroughs.parseInt(borough));
                }

                if (zip != null) {
                    params.put(ZIP_CODE, zip);
                }
                return params;
            }
        }.execute();
    }

    @Override
    public Map<String, Object> callFunction2(final String crossStreetOne, final String boroughCrossStreetOne,
            final String crossStreetTwo, final String boroughCrossStreetTwo, final String compassDirection) {
        return new Call(serviceConfiguration.function2W(), serviceConfiguration.latLongFieldSetConverter()) {
            @Override
            public Map<String, Object> userArguments() {
                Map<String, Object> params = newMap();
                params.put(STREET_NAME, crossStreetOne);
                params.put(STREET_NAME2, crossStreetTwo);
                params.put(BOROUGH_CODE, Boroughs.parseInt(boroughCrossStreetOne));
                if (boroughCrossStreetTwo != null) {
                    params.put(BOROUGH_CODE2, Boroughs.parseInt(boroughCrossStreetTwo));
                }
                if (compassDirection != null) {
                    params.put(COMPASS_DIRECTION, compassDirection);
                }
                return params;
            }
        }.execute();
    }

    @Override
    public Map<String, Object> callFunction3(final String onStreet, final String boroughOnStreet,
            final String crossStreetOne, final String boroughCrossStreetOne, final String crossStreetTwo,
            final String boroughCrossStreetTwo, final String compassDirection) {
        return new Call(serviceConfiguration.function3(), serviceConfiguration.latLongFieldSetConverter()) {
            @Override
            public Map<String, Object> userArguments() {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(STREET_NAME, onStreet);
                params.put(STREET_NAME2, crossStreetOne);
                params.put(STREET_NAME3, crossStreetTwo);
                params.put(BOROUGH_CODE, Boroughs.parseInt(boroughOnStreet));
                if (boroughCrossStreetOne != null) {
                    params.put(BOROUGH_CODE2, Boroughs.parseInt(boroughCrossStreetOne));
                }
                if (boroughCrossStreetTwo != null) {
                    params.put(BOROUGH_CODE3, Boroughs.parseInt(boroughCrossStreetTwo));
                }
                if (compassDirection != null) {
                    params.put(COMPASS_DIRECTION, compassDirection);
                }
                return params;
            }
        }.execute();
    }

    @Override
    public Map<String, Object> callFunctionBL(final String borough, final String block, final String lot) {
        return new Call(serviceConfiguration.functionBL(), serviceConfiguration.latLongFieldSetConverter()) {
            @Override
            public Map<String, Object> userArguments() {
                Map<String, Object> params = newMap();
                params.put(BBL_BOROUGH_CODE, Boroughs.parseInt(borough));
                params.put(BBL_TAX_BLOCK, block);
                params.put(BBL_TAX_LOT, lot);
                return params;
            }
        }.execute();
    }

    @Override
    public Map<String, Object> callFunctionBN(final String bin) {
        return new Call(serviceConfiguration.functionBN(), serviceConfiguration.latLongFieldSetConverter()) {
            @Override
            public Map<String, Object> userArguments() {
                Map<String, Object> params = newMap();
                params.put(BIN, bin);
                return params;
            }
        }.execute();
    }

    @Override
    public Map<String, Object> callFunctionHR() {
        return new Call(serviceConfiguration.functionHR(), serviceConfiguration.latLongFieldSetConverter()) {
            @Override
            public Map<String, Object> userArguments() {
                return Collections.emptyMap();
            }
        }.execute();
    }

    @Override
    public Map<String, Object> callGeosupport(final Map<String, Object> clientParams) {
        final Function function = serviceConfiguration
                .geosupportFunction(clientParams.get(GEOSUPPORT_FUNCTION_CODE).toString());
        return new Call(function, serviceConfiguration.latLongFieldSetConverter()) {
            @Override
            public Map<String, Object> userArguments() {
                return clientParams;
            }
        }.execute();
    }

    @Override
    public Map<String, Object> callFunctionN(String streetName, Integer length, String format) {
        return new Call(serviceConfiguration.functionN(), serviceConfiguration.latLongFieldSetConverter()) {
            @Override
            public Map<String, Object> userArguments() {
                Map<String, Object> params = newMap();

                params.put(STREET_NAME, streetName);

                if (length != null) {
                    params.put(NORMALIZATION_LENGTH, String.valueOf(length));
                }

                if (isValidNormalizationFormat(format)) {
                    params.put(NORMALIZATION_FORMAT, format);
                }

                return params;
            }
        }.execute();
    }

    private StreetCode getStreetCodeOrNull(String code) {
        if (code == null) {
            return null;
        }
        try {
            return new StreetCode(code);
        } catch (NullPointerException | InvalidStreetCodeException e) {
            LOGGER.warn("Invalid borough + street code: %s", code);
            return null;
        }
    }

    @Override
    public Map<String, Object> callStreetNameFunction(String codeOne, String codeTwo, String codeThree, Integer length, String format) {
        Assert.notNull(codeOne, STREET_CODE + " argument cannot be null");
        StreetCode streetCodeOne = getStreetCodeOrNull(codeOne);
        StreetCode streetCodeTwo = getStreetCodeOrNull(codeTwo);
        StreetCode streetCodeThree = getStreetCodeOrNull(codeThree);
        switch (streetCodeOne.getStreetCodeType()) {
            case B5SC:
                return populateStreetNameCall(serviceConfiguration.functionD(), streetCodeOne, streetCodeTwo, streetCodeThree, length, format).execute();
            case B7SC:
                return populateStreetNameCall(serviceConfiguration.functionDG(), streetCodeOne, streetCodeTwo, streetCodeThree, length, format).execute();
            case B10SC:
                return populateStreetNameCall(serviceConfiguration.functionDN(), streetCodeOne, streetCodeTwo, streetCodeThree, length, format).execute();
            default:
                throw new InvalidStreetCodeException(codeOne, "Valid street code formats: B5SC (6 chars), B7SC (8 chars), or B10SC (11 chars)");
        }
    }

    @Override
    public Map<String, Object> callFunctionD(String streetCodeOne, String streetCodeTwo, String streetCodeThree,
            Integer length, String format) {
        return callStreetNameFunction(streetCodeOne, streetCodeTwo, streetCodeThree, length, format);
    }

    @Override
    public Map<String, Object> callFunctionDG(String streetCodeOne, String streetCodeTwo, String streetCodeThree,
            Integer length, String format) {
        return callStreetNameFunction(streetCodeOne, streetCodeTwo, streetCodeThree, length, format);
    }

    @Override
    public Map<String, Object> callFunctionDN(String streetCodeOne, String streetCodeTwo, String streetCodeThree,
            Integer length, String format) {
        return callStreetNameFunction(streetCodeOne, streetCodeTwo, streetCodeThree, length, format);
    }

    private Call populateStreetNameCall(Function function, StreetCode streetCodeOne, StreetCode streetCodeTwo,
            StreetCode streetCodeThree, Integer length, String format) {
        return new Call(function, serviceConfiguration.latLongFieldSetConverter()) {
            @Override
            public Map<String, Object> userArguments() {
                Map<String, Object> params = newMap();

                params.put(BOROUGH_CODE, streetCodeOne.getBoroughCodeAsInt());
                params.put(STREET_CODE, streetCodeOne.getStreetCodeWithoutBorough());

                if (streetCodeTwo != null) {
                    params.put(BOROUGH_CODE2, streetCodeTwo.getBoroughCodeAsInt());
                    params.put(STREET_CODE2, streetCodeTwo.getStreetCodeWithoutBorough());
                }

                if (streetCodeThree != null) {
                    params.put(BOROUGH_CODE3, streetCodeThree.getBoroughCodeAsInt());
                    params.put(STREET_CODE3, streetCodeThree.getStreetCodeWithoutBorough());
                }

                if (length != null) {
                    params.put(NORMALIZATION_LENGTH, String.valueOf(length));
                }

                if (isValidNormalizationFormat(format)) {
                    params.put(NORMALIZATION_FORMAT, format);
                }

                return params;
            }
        };
    }

    private boolean isValidNormalizationFormat(String format) {
        return format != null && (NORMALIZATION_FORMAT_SORT_VALUE.equals(format) || NORMALIZATION_FORMAT_COMPACT_VALUE.equals(format));
    }

    @Override
    public Version version() {
        Map<String, Object> data = callFunctionHR();
        Version version = this.serviceConfiguration.version(data);
        return version;
    }
}

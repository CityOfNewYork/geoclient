/*
 * Copyright 2013-2016 the original author or authors.
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

import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.service.configuration.AppConfig;
import gov.nyc.doitt.gis.geoclient.service.domain.Borough;
import gov.nyc.doitt.gis.geoclient.service.domain.Documentation;
import gov.nyc.doitt.gis.geoclient.service.domain.Version;

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
        private final LatLongEnhancer latLongEnhancer;

        public Call(Function function, LatLongEnhancer latLongEnhancer) {
            super();
            this.function = function;
            this.latLongEnhancer = latLongEnhancer;
        }

        // TODO Refactor for brevity or leave as is for clarity?
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
            latLongEnhancer.addLatLong(result);
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
    public Map<String, Object> callFunction1B(final String houseNumber, final String street, final String borough,
            final String zip) {
        return new Call(serviceConfiguration.function1B(), serviceConfiguration.latLongEnhancer()) {
            @Override
            public Map<String, Object> userArguments() {
                Map<String, Object> params = newMap();
                if (houseNumber != null) {
                    params.put(HOUSE_NUMBER, houseNumber);
                }

                params.put(STREET_NAME, street);

                if (borough != null) {
                    params.put(BOROUGH_CODE, Borough.parseInt(borough));
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
        return new Call(serviceConfiguration.function2(), serviceConfiguration.latLongEnhancer()) {
            @Override
            public Map<String, Object> userArguments() {
                Map<String, Object> params = newMap();
                params.put(STREET_NAME, crossStreetOne);
                params.put(STREET_NAME2, crossStreetTwo);
                params.put(BOROUGH_CODE, Borough.parseInt(boroughCrossStreetOne));
                if (boroughCrossStreetTwo != null) {
                    params.put(BOROUGH_CODE2, Borough.parseInt(boroughCrossStreetTwo));
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
        return new Call(serviceConfiguration.function3(), serviceConfiguration.latLongEnhancer()) {
            @Override
            public Map<String, Object> userArguments() {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(STREET_NAME, onStreet);
                params.put(STREET_NAME2, crossStreetOne);
                params.put(STREET_NAME3, crossStreetTwo);
                params.put(BOROUGH_CODE, Borough.parseInt(boroughOnStreet));
                if (boroughCrossStreetOne != null) {
                    params.put(BOROUGH_CODE2, Borough.parseInt(boroughCrossStreetOne));
                }
                if (boroughCrossStreetTwo != null) {
                    params.put(BOROUGH_CODE3, Borough.parseInt(boroughCrossStreetTwo));
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
        return new Call(serviceConfiguration.functionBL(), serviceConfiguration.latLongEnhancer()) {
            @Override
            public Map<String, Object> userArguments() {
                Map<String, Object> params = newMap();
                params.put(BBL_BOROUGH_CODE, Borough.parseInt(borough));
                params.put(BBL_TAX_BLOCK, block);
                params.put(BBL_TAX_LOT, lot);
                return params;
            }
        }.execute();
    }

    @Override
    public Map<String, Object> callFunctionBN(final String bin) {
        return new Call(serviceConfiguration.functionBN(), serviceConfiguration.latLongEnhancer()) {
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
        return new Call(serviceConfiguration.functionHR(), serviceConfiguration.latLongEnhancer()) {
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
        return new Call(function, serviceConfiguration.latLongEnhancer()) {
            @Override
            public Map<String, Object> userArguments() {
                return clientParams;
            }
        }.execute();
    }

    public Documentation getDocumentation() {
        return new DocumentationConfigurer(serviceConfiguration.geosupportConfiguration(),
                serviceConfiguration.latLongEnhancer()).configureDocumentation();
    }

    @Override
    public Version version() {
        Map<String, Object> data = callFunctionHR();
        Version version = this.serviceConfiguration.version(data);
        return version;
    }
}

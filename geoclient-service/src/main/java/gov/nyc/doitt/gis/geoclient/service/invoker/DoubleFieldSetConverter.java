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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.service.domain.FieldSet;

/**
 * Selectively converts {@link String} values returned by
 * Geosupport to {@link Double} values. Assumes responsibility for knowing which
 * functions contain results targeted for conversion using
 * {@link FieldSet}s.
 *
 * @author Matthew Lipper
 * @since 2.0
 * @see FieldSet
 * @see FieldSetConverter
 */
public class DoubleFieldSetConverter implements FieldSetConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoubleFieldSetConverter.class);

    private Map<String, FieldSet> fieldSets;

    public DoubleFieldSetConverter(List<FieldSet> fieldSets) {
        this.fieldSets = new HashMap<>();
        for (FieldSet fieldSet : fieldSets) {
            this.fieldSets.put(fieldSet.getFunctionId(), fieldSet);
        }
    }

    /**
     * Converts {@link String} values to their {@link Double} equivalents for any
     * matching field names (keys in the arguments parameter) when configured with
     * FieldSets with the same function identifier.
     *
     * @see FieldSetConverter
     */
    @Override
    public void convert(String functionId, Map<String, Object> arguments) {
        LOGGER.debug("Checking if function {} has fields requiring conversion...");
        if (this.fieldSets.containsKey(functionId)) {
            FieldSet fieldSet = this.fieldSets.get(functionId);
            LOGGER.debug("Converting function {} fields {} from Strings to Doubles...", functionId,
                    fieldSet.getFieldNames());
            convert(arguments, fieldSet.getFieldNames());
        }
    }

    protected void convert(Map<String, Object> arguments, Set<String> fieldNames) {
        for (String name : fieldNames) {
            LOGGER.debug("Checking for field name {}...", name);
            if (arguments.containsKey(name)) {
                Object value = arguments.get(name);
                LOGGER.debug("Current value of field {}: {}", name, value);
                if (value != null) {
                    String stringValue = value.toString();
                    Double doubleValue = convert(stringValue);
                    arguments.put(name, doubleValue);
                    LOGGER.debug("Converted field {} from String: {} to Double: {}", name, stringValue, doubleValue);
                }
            }
        }
    }

    private Double convert(String coordinateString) {
        if(coordinateString == null) {
            throw new IllegalArgumentException("Coordinate string must not be null");
        }
        String trimmed = coordinateString.trim();
        return Double.valueOf(trimmed);
    }
}

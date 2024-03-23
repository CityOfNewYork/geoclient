/*
 * Copyright 2013-2024 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.docs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.util.StringUtils.hasText;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.docs.Sample.Builder;

public class SampleAggregator implements ArgumentsAggregator {

    private static final Logger logger = LoggerFactory.getLogger(SampleAggregator.class);

    @Override
    public Sample aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
        String id = arguments.getString(0);
        PathVariable pathVariable = PathVariable.fromString(arguments.getString(1));
        String description = arguments.getString(2);
        Builder builder = new Sample.Builder(id, pathVariable, description);
        switch (pathVariable) {
            case ADDRESS:
            case ADDRESS_POINT:
                addParamIfNotNull(
                    Arrays.asList("houseNumber", "street", "borough", "zip"),
                    Arrays.asList(3,4,5,6),
                    arguments,
                    builder);
                break;
            case BBL:
                addParamIfNotNull(
                    Arrays.asList("borough", "block", "lot"),
                    Arrays.asList(3,4,5),
                    arguments,
                    builder);
                break;
            case BIN:
                addParamIfNotNull(
                    Arrays.asList("bin"),
                    Arrays.asList(3),
                    arguments,
                    builder);
                break;
            case BLOCKFACE:
                addParamIfNotNull(
                    Arrays.asList("onStreet", "crossStreetOne", "crossStreetTwo", "borough", "boroughCrossStreetOne", "boroughCrossStreetTwo", "compassDirection"),
                    Arrays.asList(3,4,5,6,7,8,9),
                    arguments,
                    builder);
                break;
            case INTERSECTION:
                addParamIfNotNull(
                    Arrays.asList("crossStreetOne", "crossStreetTwo", "borough", "boroughCrossStreetTwo", "compassDirection"),
                    Arrays.asList(3,4,5,6,7),
                    arguments,
                    builder);
                break;
            case PLACE:
                addParamIfNotNull(
                    Arrays.asList("name", "borough", "zip"),
                    Arrays.asList(3,4,5),
                    arguments,
                    builder);
                break;
            case SEARCH:
                addParamIfNotNull(
                    Arrays.asList("input", "returnTokens", "returnRejections", "returnPossiblesWithExactMatch", "returnPolicy"),
                    Arrays.asList(3,4,5,6,7),
                    arguments,
                    builder);
                // Not implemented yet (they use default values):
                // exactMatchMaxLevel, exactMatchForSingleSuccess, maxDepth, similarNamesDistance
                break;
            case STREETCODE:
                addParamIfNotNull(
                    Arrays.asList("streetCode", "streetCodeTwo", "streetCodeThree", "length", "format"),
                    Arrays.asList(3,4,5,6,7),
                    arguments,
                    builder);
                break;
            case NORMALIZE:
                addParamIfNotNull(
                    Arrays.asList("name", "length", "format"),
                    Arrays.asList(3,4,5),
                    arguments,
                    builder);
                break;
            case VERSION:
                // Does not accept query parameters
                break;
            default:
                throw new IllegalStateException(String.format("Unrecognized PathVariable: {}", pathVariable));
        }
        return builder.build();
    }

    private void addParamIfNotNull(List<String> names, List<Integer> indices, ArgumentsAccessor arguments, Sample.Builder builder) {
        logger.info("Checking arguments {}.", arguments);
        assertEquals(names.size(), indices.size());
        int i = names.size();
        for (int j = 0; j < i; j++) {
            String name = names.get(j);
            int argIndex = indices.get(j);
            String value = arguments.getString(argIndex);
            if (hasText(value)) {
                builder.queryParam(name, value);
                logger.info("Added query parameter {}:{}", name, value);
            }
        }
    }
}

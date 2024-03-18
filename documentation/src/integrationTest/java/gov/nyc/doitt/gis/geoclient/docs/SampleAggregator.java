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

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import gov.nyc.doitt.gis.geoclient.docs.Sample.Builder;

public class SampleAggregator implements ArgumentsAggregator {
    @Override
    public Sample aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
        String id = arguments.getString(0);
        PathVariable pathVariable = PathVariable.fromString(arguments.getString(1));
        String description = arguments.getString(2);
        Builder builder = new Sample.Builder(id, pathVariable, description);
        switch (pathVariable) {
            case ADDRESS:
            case ADDRESS_POINT:
                builder.queryParam("houseNumber", arguments.getString(3));
                builder.queryParam("street", arguments.getString(4));
                builder.queryParam("borough", arguments.getString(5));
                builder.queryParam("zip", arguments.getString(6));
                break;
            case BBL:
                builder.queryParam("borough", arguments.getString(3));
                builder.queryParam("block", arguments.getString(4));
                builder.queryParam("lot", arguments.getString(5));
                break;
            case BIN:
                builder.queryParam("bin", arguments.getString(3));
                break;
            case BLOCKFACE:
                builder.queryParam("onStreet", arguments.getString(3));
                builder.queryParam("crossStreetOne", arguments.getString(4));
                builder.queryParam("crossStreetTwo", arguments.getString(5));
                builder.queryParam("borough", arguments.getString(6));
                builder.queryParam("boroughCrossStreetOne", arguments.getString(7));
                builder.queryParam("boroughCrossStreetTwo", arguments.getString(8));
                builder.queryParam("compassDirection", arguments.getString(9));
                break;
            case INTERSECTION:
                builder.queryParam("crossStreetOne", arguments.getString(3));
                builder.queryParam("crossStreetTwo", arguments.getString(4));
                builder.queryParam("borough", arguments.getString(5));
                builder.queryParam("boroughCrossStreetTwo", arguments.getString(6));
                builder.queryParam("compassDirection", arguments.getString(7));
                break;
            case PLACE:
                builder.queryParam("name", arguments.getString(3));
                builder.queryParam("borough", arguments.getString(4));
                builder.queryParam("zip", arguments.getString(5));
                break;
            case SEARCH:
                builder.queryParam("input", arguments.getString(3));
                builder.queryParam("returnTokens", arguments.getString(4));
                builder.queryParam("returnRejections", arguments.getString(5));
                builder.queryParam("returnPossiblesWithExactMatch", arguments.getString(6));
                builder.queryParam("returnPolicy", arguments.getString(7));
                // Not implemented yet (they use default values):
                // exactMatchMaxLevel, exactMatchForSingleSuccess, maxDepth, similarNamesDistance
                break;
            case STREETCODE:
                builder.queryParam("streetCode", arguments.getString(3));
                builder.queryParam("streetCodeTwo", arguments.getString(4));
                builder.queryParam("streetCodeThree", arguments.getString(5));
                builder.queryParam("length", arguments.getString(6));
                builder.queryParam("format", arguments.getString(7));
                break;
            case NORMALIZE:
                builder.queryParam("name", arguments.getString(3));
                builder.queryParam("length", arguments.getString(4));
                builder.queryParam("format", arguments.getString(5));
                break;
            case VERSION:
                // Does not accept query parameters
                break;
            default:
                throw new IllegalStateException(String.format("Unrecognized PathVariable: {}", pathVariable));
        }
        return builder.build();
    }

}

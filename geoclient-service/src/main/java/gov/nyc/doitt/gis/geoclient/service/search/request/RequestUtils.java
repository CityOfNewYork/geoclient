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
package gov.nyc.doitt.gis.geoclient.service.search.request;

import gov.nyc.doitt.gis.geoclient.parser.LocationTokens;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;
import gov.nyc.doitt.gis.geoclient.service.search.InputValue;

public class RequestUtils
{
    public static <T extends Request> T initialRequest(Class<T> type, LocationTokens locationTokens, InputValue countyInputValue)
    {
        if(type.equals(AddressRequest.class))
        {
            return type.cast(zeroLevelAddressRequest(locationTokens, countyInputValue));
        }

        if(type.equals(BblRequest.class))
        {
            return type.cast(zeroLevelBblRequest(locationTokens, countyInputValue));
        }

        if(type.equals(BinRequest.class))
        {
            return type.cast(zeroLevelBinRequest(locationTokens));
        }

        if(type.equals(BlockfaceRequest.class))
        {
            return type.cast(zeroLevelBlockfaceRequest(locationTokens, countyInputValue));
        }

        if(type.equals(IntersectionRequest.class))
        {
            return type.cast(zeroLevelIntersectionRequest(locationTokens, countyInputValue));
        }

        if (type.equals(PlaceRequest.class))
        {
            return type.cast(zeroLevelPlaceRequest(locationTokens, countyInputValue));
        }

        throw new IllegalArgumentException("Unknown CallBuilder type " + type);
    }

    private static AddressRequest zeroLevelAddressRequest(LocationTokens locationTokens, InputValue countyInputValue)
    {
        AddressRequest request = new AddressRequest();
        request.setBasicHouseNumberInputValue(inputValueOrNull(TokenType.HOUSE_NUMBER, locationTokens));
        request.setHouseNumberSuffixInputValue(inputValueOrNull(TokenType.HOUSE_NUMBER_SUFFIX, locationTokens));
        request.setStreetInputValue(inputValueOrNull(TokenType.STREET_NAME, locationTokens));
        setLevelAndBoroughOrZip(request, 0, countyInputValue);
        return request;
    }

    private static BblRequest zeroLevelBblRequest(LocationTokens locationTokens, InputValue countyInputValue)
    {
        BblRequest request = new BblRequest();
        request.setBlockInputValue(inputValueOrNull(TokenType.BLOCK, locationTokens));
        request.setLotInputValue(inputValueOrNull(TokenType.LOT, locationTokens));
        setLevelAndBoroughOrZip(request, 0, countyInputValue);
        return request;
    }

    private static BinRequest zeroLevelBinRequest(LocationTokens locationTokens)
    {
        BinRequest request = new BinRequest();
        request.setBinInputValue(inputValueOrNull(TokenType.BIN, locationTokens));
        return request;
    }

    private static BlockfaceRequest zeroLevelBlockfaceRequest(LocationTokens locationTokens, InputValue countyInputValue)
    {
        BlockfaceRequest request = new BlockfaceRequest();
        request.setOnStreetInputValue(inputValueOrNull(TokenType.ON_STREET, locationTokens));
        request.setCrossStreetOneInputValue(inputValueOrNull(TokenType.CROSS_STREET_ONE, locationTokens));
        request.setCrossStreetTwoInputValue(inputValueOrNull(TokenType.CROSS_STREET_TWO, locationTokens));
        setLevelAndBoroughOrZip(request, 0, countyInputValue);
        return request;
    }

    private static IntersectionRequest zeroLevelIntersectionRequest(LocationTokens locationTokens, InputValue countyInputValue)
    {
        IntersectionRequest request = new IntersectionRequest();
        request.setCrossStreetOneInputValue(inputValueOrNull(TokenType.CROSS_STREET_ONE, locationTokens));
        request.setCrossStreetTwoInputValue(inputValueOrNull(TokenType.CROSS_STREET_TWO, locationTokens));
        setLevelAndBoroughOrZip(request, 0, countyInputValue);
        return request;
    }

    private static PlaceRequest zeroLevelPlaceRequest(LocationTokens locationTokens, InputValue countyInputValue)
    {
        PlaceRequest request = new PlaceRequest();
        request.setStreetInputValue(inputValueOrNull(TokenType.UNRECOGNIZED, locationTokens));
        setLevelAndBoroughOrZip(request, 0, countyInputValue);
        return request;
    }

    private static void setLevelAndBoroughOrZip(CountyRequest request, int baseLevel, InputValue countyInputValue)
    {
        if(TokenType.ZIP.equals(countyInputValue.getTokenType()))
        {
            request.setZipInputValue(countyInputValue);
        } else {
            // Assume it's a Borough
            request.setBoroughInputValue(countyInputValue);
        }
        int level = baseLevel;
        if(countyInputValue != null && countyInputValue.isAssigned())
        {
            // If the borough is not from parsed user input, increase the level
            // to indicate that it was "guessed"
            level++;
        }
        request.setLevel(level);
    }

    private static InputValue inputValueOrNull(TokenType tokenType, LocationTokens locationTokens)
    {
        Token token = locationTokens.firstTokenOfType(tokenType);
        if(token == null)
        {
            return null;
        }
        return new InputValue(token);
    }

}

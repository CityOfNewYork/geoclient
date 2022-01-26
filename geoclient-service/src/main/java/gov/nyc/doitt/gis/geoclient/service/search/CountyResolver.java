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
package gov.nyc.doitt.gis.geoclient.service.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.nyc.doitt.gis.geoclient.parser.LocationTokens;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

public class CountyResolver
{
    public static final InputValue MANHATTAN = new InputValue(TokenType.BOROUGH_NAME, "MANHATTAN");
    public static final InputValue BRONX = new InputValue(TokenType.BOROUGH_NAME, "BRONX");
    public static final InputValue BROOKLYN = new InputValue(TokenType.BOROUGH_NAME, "BROOKLYN");
    public static final InputValue QUEENS = new InputValue(TokenType.BOROUGH_NAME, "QUEENS");
    public static final InputValue STATEN_ISLAND = new InputValue(TokenType.BOROUGH_NAME, "STATEN ISLAND");

    private final Map<String, String> mappings = new HashMap<String, String>();

    @SafeVarargs
    public CountyResolver(Map<String, String>... mappings)
    {
        super();
        for (int i = 0; i < mappings.length; i++)
        {
            add(mappings[i]);
        }
    }

    public ValueResolution resolve(LocationTokens locationTokens)
    {
        ValueResolution valueResolution = new ValueResolution();
        addUnmappedTokens(locationTokens, valueResolution);
        addMappedTokens(locationTokens, valueResolution);
        if(valueResolution.resolvedCount() == 0)
        {
            valueResolution.add(MANHATTAN);
            valueResolution.add(BRONX);
            valueResolution.add(BROOKLYN);
            valueResolution.add(QUEENS);
            valueResolution.add(STATEN_ISLAND);
        }
        return valueResolution;
    }
    protected void addUnmappedTokens(LocationTokens locationTokens, ValueResolution valueResolution)
    {
        List<Token> boroughishTokens = locationTokens.tokensOfType(TokenType.ZIP);
        for (Token token : boroughishTokens)
        {
            valueResolution.add(new InputValue(token));
        }
    }

    protected void addMappedTokens(LocationTokens locationTokens, ValueResolution valueResolution)
    {
        List<Token> boroughishTokens = locationTokens.tokensOfType(TokenType.BOROUGH_NAME, TokenType.BOROUGH_CODE, TokenType.CITY_NAME);
        for (Token token : boroughishTokens)
        {
            String upperCaseValue = token.getValue().toUpperCase();
            valueResolution.add(new InputValue(token, mappings.get(upperCaseValue)));
        }
    }

    protected void add(Map<String, String> map)
    {
        for(Map.Entry<String, String> entry: map.entrySet())
        {
            this.mappings.put(entry.getKey().toUpperCase(), entry.getValue());
        }
    }
}

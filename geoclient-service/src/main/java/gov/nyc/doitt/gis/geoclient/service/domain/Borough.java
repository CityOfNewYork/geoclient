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
package gov.nyc.doitt.gis.geoclient.service.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

public class Borough
{

    private static final String MANHATTAN = "MANHATTAN";
    private static final String BRONX = "BRONX";
    private static final String BROOKLYN = "BROOKLYN";
    private static final String QUEENS = "QUEENS";
    private static final String STATEN_ISLAND = "STATEN ISLAND";

    @SuppressWarnings("serial")
    private static final Map<String, Integer> NAMES = new HashMap<String, Integer>()
    {
        {
            put(MANHATTAN, 1);
            put("MN", 1);
            put(BRONX, 2);
            put("BX", 2);
            put(BROOKLYN, 3);
            put("BK", 3);
            put(QUEENS, 4);
            put("QN", 4);
            put(STATEN_ISLAND, 5);
            put("SI", 5);
            put("STATEN IS", 5);
        }
    };

    public static int parseInt(String boroughString)
    {
        if (NumberUtils.isParsable(boroughString))
        {
            return Integer.valueOf(boroughString);
        }

        for (Map.Entry<String, Integer> entry : NAMES.entrySet())
        {
            if (entry.getKey().equalsIgnoreCase(boroughString))
            {
                return entry.getValue();
            }
        }

        return 0;
    }
}

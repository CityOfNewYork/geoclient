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
package gov.nyc.doitt.gis.geoclient.parser.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexSandboxTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RegexSandboxTest.class);
    private static final Pattern USA = Pattern.compile("\\b(US|USA|UNITED STATES OF AMERICA|AMERICA)\\b",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern ENDS_WITH_USA = Pattern.compile(USA.pattern()+ "\\s*$",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern ENDS_WITH_ZIP = Pattern.compile("((?:\\d{5})(?:-\\d{4})?)(?:(\\s|[,])+USA)?\\s*$",
            Pattern.CASE_INSENSITIVE);

    private static final String REGEX =
            "(?:F/O|IFO|I/F/O|FO)?\\b\\s*" +
            "(\\d+(?:[A-Z]{1}\\b)?)" +
            "(\\s?[A-Z]-[A-Z]\\b)?" +
            "(\\s?-{1,2}\\s?\\w+(?:/\\d+)?)?" +
            "((?:\\s?\\d+/\\d+(?:-[A-Z])?){0,2})" +
            "(\\s?\\d+/\\d+)?" +
            "(\\s?\\d*\\s?(?:REAR|INTER|GARAGE|GAR|GARAG|FLOOR|FL|AIRRGTS|FRTA|BB|AB|BA|AA|ABCD|RSTU)\\b(?:-[A-Z])?)?" +
            "(\\s?\\d*\\s?[A-Z&&[^NSEW]]{1}\\b(?!'))?" +
            "(\\s?\\d+[A-Z]{1}\\b)?" +
            "(\\s?[A-Z&&[^NSEW]]{1}\\d+\\b(?:/\\d)?)?" +
            "(\\s?&?\\d+\\s+(?=\\d+))?" +
            "(#\\d)?" +
            "(.*)";
    private static final Pattern STARTS_WITH_HOUSE_NUMBER = Pattern.compile(REGEX,Pattern.CASE_INSENSITIVE);

    private String[] usa = new String[]{"USA should not a match","USA America US","","xxxUSA","xxx,USA","xxx,USA ","xxx USA", "xxx USA ","xxx , USA  ","This is the United States of America", "xxx America  "," US"};
    private String[] addresses = new String[] { "123 Main St., Manhattan, NY, 11234",
            "123 Main St., Manhattan NY   11234  ", "123 Main St., Manhattan NY   11234 USA",
            "123 Main St.,Manhattan,NY,11234-4321,USA", "123 Main St., Manhattan NY   11234 , USA ", "sdsdfvdfdfgd",
            "11234", "123 Main Street Manhattan" };
    private String[] houseNumbers = new String[]{
            "280 RSD",
            "519 Front East 12th Street, Manhattan",
            "625 Rear Smith Street, Brooklyn",
            "120 1/2 First Avenue, Manhattan",
            "240-55 1/3 Depew Avenue, Queens",
            "469 1/4 Father Capodanno Blvd.",
            "470 A West 43rd Street, Manhattan",
            "171C Auburn Avenue, Staten Island",
            "20-29 Garage 120th Street, Queens",
    };
    @Test
    public void testHouseNumber()
    {
        for (int i = 0; i < houseNumbers.length; i++)
        {
            String string = houseNumbers[i];
            Matcher matcher = STARTS_WITH_HOUSE_NUMBER.matcher(string);
            showMatch(string, matcher);
        }
    }

    //@Test
    public void testEndsWithUsa()
    {
        for (int i = 0; i < usa.length; i++)
        {
            String string = usa[i];
            Matcher matcher = ENDS_WITH_USA.matcher(string);
            showMatch(string, matcher);
        }
    }

    //@Test
    public void testEndsWithZip()
    {
        for (int i = 0; i < addresses.length; i++)
        {
            String string = addresses[i];
            Matcher matcher = ENDS_WITH_ZIP.matcher(string);
            showMatch(string, matcher);
        }
    }

    private void showMatch(String input, Matcher matcher)
    {

        LOGGER.debug("\"{}\" - {} characters", input, input.length());
        LOGGER.debug("==========================================");

        if (matcher.matches())
        {
            LOGGER.debug("Complete match");
            logGroups(matcher);
        } else
        {
            boolean found = false;
            while (matcher.find())
            {
                logGroups(matcher);
                found = true;
            }
            if (!found)
            {
                LOGGER.debug("No matches.");
                LOGGER.debug("");
            }
        }
    }

    private void logGroups(Matcher matcher)
    {
        for (int j = 0; j <= matcher.groupCount(); j++)
        {
            LOGGER.debug("GROUP{}[{} to {}]: \"{}\"", j, matcher.start(j), matcher.end(j), matcher.group(j));
            LOGGER.debug("");
        }
    }
}

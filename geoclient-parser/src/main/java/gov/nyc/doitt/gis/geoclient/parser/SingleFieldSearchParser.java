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
package gov.nyc.doitt.gis.geoclient.parser;

import java.util.List;


public class SingleFieldSearchParser implements LocationTokenizer
{
    private List<Parser> parsers;

    public SingleFieldSearchParser(List<Parser> parsers)
    {
        super();
        this.parsers = parsers;
    }

    @Override
    public LocationTokens parse(Input input)
    {
        ParseContext parseContext = new ParseContext(input);
        for (Parser parser : this.parsers)
        {
            parser.parse(parseContext);
            if(parseContext.isParsed())
            {
                break;
            }
        }

        return new LocationTokens(input, parseContext.getChunks());
    }
}

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
package gov.nyc.doitt.gis.geoclient.parser.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.util.CollectionUtils;

import gov.nyc.doitt.gis.geoclient.parser.Parser;
import gov.nyc.doitt.gis.geoclient.parser.SingleFieldSearchParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.AddressParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.BblParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.BinParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.BlockfaceParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.BoroughParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.CityParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.CountryParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.IntersectionParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.UnrecognizedTextParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.ZipParser;

@Configuration
@ImportResource("classpath:regexDataContext.xml")
public class ParserConfig
{
    //public static final String BOROUGH_NAMES = "(MANHATTAN|MN|BRONX|BX|BRX|BROOKLYN|BK|BKLYN|QUEENS|QNS|QN|QS|STATEN\\s?IS(?:\\.|LAND)?|SI)";
    public static final String NY_CITY = "(New York City|N\\.?Y\\.?C\\.?)";
    public static final String STATES = "(Alabama|Alaska|Arizona|Arkansas|California|Colorado|Connecticut|Delaware|Florida|Georgia|Hawaii|Idaho|Illinois|Indiana|Iowa|Kansas|Kentucky|Louisiana|Maine|Maryland|Massachusetts|Michigan|Minnesota|Mississippi|Missouri|Montana|Nebraska|Nevada|New Hampshire|New Jersey|New Mexico|New York|North Carolina|North Dakota|Ohio|Oklahoma|Oregon|Pennsylvania|Rhode Island|South Carolina|South Dakota|Tennessee|Texas|Utah|Vermont|Virginia|Washington|West Virginia|Wisconsin|Wyoming|Washington DC|Puerto Rico|U\\.?S\\.? Virgin Islands|American Samoa|Guam|Northern Mariana Islands|A\\.?L\\.?|A\\.?K\\.?|A\\.?Z\\.?|A\\.?R\\.?|C\\.?A\\.?|C\\.?O\\.?|C\\.?T\\.?|D\\.?E\\.?|F\\.?L\\.?|G\\.?A\\.?|H\\.?I\\.?|I\\.?D\\.?|I\\.?L\\.?|I\\.?N\\.?|I\\.?A\\.?|K\\.?S\\.?|K\\.?Y\\.?|L\\.?A\\.?|M\\.?E\\.?|M\\.?D\\.?|M\\.?A\\.?|M\\.?I\\.?|M\\.?N\\.?|M\\.?S\\.?|M\\.?O\\.?|M\\.?T\\.?|N\\.?E\\.?|N\\.?V\\.?|N\\.?H\\.?|N\\.?J\\.?|N\\.?M\\.?|N\\.?Y\\.?|N\\.?C\\.?|N\\.?D\\.?|O\\.?H\\.?|O\\.?K\\.?|O\\.?R\\.?|P\\.?A\\.?|R\\.?I\\.?|S\\.?C\\.?|S\\.?D\\.?|T\\.?N\\.?|T\\.?X\\.?|U\\.?T\\.?|V\\.?T\\.?|V\\.?A\\.?|W\\.?A\\.?|W\\.?V\\.?|W\\.?I\\.?|W\\.?Y\\.?|D\\.?C\\.?|P\\.?R\\.?|V\\.?I\\.?|A\\.?S\\.?|G\\.?U\\.?|M\\.?P\\.?)";

    @Autowired
    @Qualifier("cityNameProperties") // must match bean id in imported xml resource
    private Properties cityNameProperties;

    @Autowired
    @Qualifier("boroughNameProperties") // must match bean id in imported xml resource
    private Properties boroughNameProperties;


    @Bean(name="boroughNamesToBoroughMap")
    public Map<String,String> boroughNamesToBoroughMap()
    {
        Map<String, String> map = new TreeMap<>();
        CollectionUtils.mergePropertiesIntoMap(boroughNameProperties, map);
        return map;
    }

    @Bean(name="cityNamesToBoroughMap")
    public Map<String, String> cityNamesToBoroughMap()
    {
        Map<String, String> map = new TreeMap<>();
        CollectionUtils.mergePropertiesIntoMap(cityNameProperties, map);
        return map;
    }

    @Bean
    public Set<String> cityNames()
    {
        return cityNamesToBoroughMap().keySet();
    }

    @Bean
    public Set<String> boroughNames()
    {
        return boroughNamesToBoroughMap().keySet();
    }

    @Bean
    public AddressParser addressParser()
    {
        return new AddressParser();
    }

    @Bean
    public BblParser bblParser()
    {
        return new BblParser();
    }

    @Bean
    public BinParser binParser()
    {
        return new BinParser();
    }

    @Bean
    public BlockfaceParser blockfaceParser()
    {
        return new BlockfaceParser();
    }

    @Bean
    public BoroughParser boroughParser()
    {
        return new BoroughParser(boroughNames());
    }

    @Bean
    public CityParser cityParser()
    {
        return new CityParser(cityNames());
    }

    @Bean
    public CountryParser countryParser()
    {
        return new CountryParser();
    }

    @Bean
    public IntersectionParser intersectionParser()
    {
        return new IntersectionParser();
    }

    @Bean
    public ZipParser zipParser()
    {
        return new ZipParser();
    }

    //@Bean
    public UnrecognizedTextParser unrecognizedTextParser()
    {
        return new UnrecognizedTextParser();
    }

    @Bean
    public SingleFieldSearchParser singleFieldSearchParser()
    {
        List<Parser> parsers = new ArrayList<>();
        parsers.add(bblParser());
        parsers.add(binParser());
        parsers.add(countryParser());
        parsers.add(zipParser());
        parsers.add(boroughParser());
        parsers.add(cityParser());
        parsers.add(blockfaceParser());
        parsers.add(intersectionParser());
        parsers.add(addressParser());
        parsers.add(unrecognizedTextParser());
        return new SingleFieldSearchParser(parsers);
    }


}

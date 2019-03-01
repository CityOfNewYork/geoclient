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
package gov.nyc.doitt.gis.geoclient.service.configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.oxm.xstream.XStreamMarshaller;

//import com.github.dozermapper.core.DozerBeanMapperBuilder;
//import com.github.dozermapper.core.Mapper;
import com.thoughtworks.xstream.converters.ConverterMatcher;

import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.jni.Geoclient;
import gov.nyc.doitt.gis.geoclient.jni.GeoclientJni;
import gov.nyc.doitt.gis.geoclient.parser.LocationTokenizer;
import gov.nyc.doitt.gis.geoclient.parser.configuration.ParserConfig;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.service.domain.BadRequest;
import gov.nyc.doitt.gis.geoclient.service.domain.FileInfo;
import gov.nyc.doitt.gis.geoclient.service.domain.Version;
import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportService;
import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportServiceImpl;
import gov.nyc.doitt.gis.geoclient.service.invoker.LatLongEnhancer;
import gov.nyc.doitt.gis.geoclient.service.mapper.Mapper;
import gov.nyc.doitt.gis.geoclient.service.search.CountyResolver;
import gov.nyc.doitt.gis.geoclient.service.search.SearchId;
import gov.nyc.doitt.gis.geoclient.service.search.SingleFieldSearchHandler;
import gov.nyc.doitt.gis.geoclient.service.search.task.DefaultInitialSearchTaskBuilder;
import gov.nyc.doitt.gis.geoclient.service.search.task.DefaultSpawnedTaskBuilder;
import gov.nyc.doitt.gis.geoclient.service.search.task.InitialSearchTaskBuilder;
import gov.nyc.doitt.gis.geoclient.service.search.task.SearchTaskFactory;
import gov.nyc.doitt.gis.geoclient.service.search.task.SpawnedSearchTaskBuilder;
import gov.nyc.doitt.gis.geoclient.service.xstream.MapConverter;

/**
 * Java-based configuration for the <code>geoclient-service</code> application.
 *
 * @author mlipper
 * @since 1.0
 */
@Configuration
@PropertySource(value = "classpath:version.properties")
public class AppConfig {
    @Autowired
    private Environment env;

    @Autowired
    private ParserConfig parserConfig;

    @Bean
    public Geoclient geoclient() {
        return new GeoclientJni();
    }

    @Bean
    public SingleFieldSearchHandler singleFieldSearchHandler() {
        return new SingleFieldSearchHandler(searchId(), parserConfig.singleFieldSearchParser(), searchBuilder());
    }

    @Bean
    public SearchTaskFactory searchBuilder() {
        return new SearchTaskFactory(initialSearchTaskBuilder(), spawnedSearchTaskBuilder());
    }

    @Bean
    public InitialSearchTaskBuilder initialSearchTaskBuilder() {
        return new DefaultInitialSearchTaskBuilder(countyResolver(), geosupportService(), beanMapper());
    }

    @Bean
    public SpawnedSearchTaskBuilder spawnedSearchTaskBuilder() {
        return new DefaultSpawnedTaskBuilder(countyResolver(), geosupportService(), beanMapper());
    }

    @Bean
    public CountyResolver countyResolver() {
        return new CountyResolver(parserConfig.boroughNamesToBoroughMap(), parserConfig.cityNamesToBoroughMap());
    }

    @Bean
    public SearchId searchId() {
        return new SearchId(hostname());
    }

    @Bean
    public String hostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "UnknownHost";
        }
    }

    @Bean
    public GeosupportConfig geosupportConfiguration() {
        return new GeosupportConfig(geoclient());
    }

    @Bean
    public GeosupportService geosupportService() {
        return new GeosupportServiceImpl(this);
    }

    @Bean
    public LatLongEnhancer latLongEnhancer() {
        return new LatLongEnhancer();
    }

    @Bean
    public gov.nyc.doitt.gis.geoclient.service.mapper.Mapper beanMapper() {
        // Expects a mapping file called dozerBeanMapping.xml to be on the
        // classpath. Singleton wrapper insures that mapping file is only
        // parsed once.
        // return DozerBeanMapperSingletonWrapper.getInstance();
        return new Mapper(com.github.dozermapper.core.DozerBeanMapperBuilder.buildDefault());
    }

    public Function geosupportFunction(String id) {
        return geosupportConfiguration().getFunction(id);
    }

    public Function function1B() {
        return geosupportFunction(Function.F1B);
    }

    public Function functionBL() {
        return geosupportFunction(Function.FBL);
    }

    public Function functionBN() {
        return geosupportFunction(Function.FBN);
    }

    public Function function3() {
        return geosupportFunction(Function.F3);
    }

    public Function function2() {
        return geosupportFunction(Function.F2);
    }

    public Function functionHR() {
        return geosupportFunction(Function.FHR);
    }

    // Do not declare as @Bean, but as a regular method
    // since we don't want proxies generated for incoming args
    public Version version(Map<String, Object> functionHrData) {

        Version version = new Version();
        beanMapper().map(functionHrData, version);
        version.setGeoclientJniVersion(getImplementationVersion(Geoclient.class));
        version.setGeoclientVersion(getImplementationVersion(GeosupportConfig.class));
        version.setGeoclientParserVersion(getImplementationVersion(LocationTokenizer.class));
        version.setGeoclientServiceVersion(env.getProperty("service.version", "error"));
        version.setAccessMethod("Local/JNI");
        return version;
    }

    @Bean
    public XStreamMarshaller marshaller() {
        XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setConverters(new ConverterMatcher[] { new MapConverter() });
        Map<String, Class<?>> aliases = new HashMap<String, Class<?>>();
        aliases.put("geosupportResponse", Map.class);
        aliases.put("version", Version.class);
        aliases.put("fileInfo", FileInfo.class);
        aliases.put("error", BadRequest.class);
        aliases.put("chunk", Chunk.class);
        aliases.put("token", Token.class);
        marshaller.setAliases(aliases);
        marshaller.setAutodetectAnnotations(true);
        return marshaller;
    }

    public String getImplementationVersion(Class<?> clazz) {
        Package pkg = clazz.getPackage();

        if (pkg == null) {
            return "UNKNOWN";
        }
        return pkg.getImplementationVersion();
    }

}

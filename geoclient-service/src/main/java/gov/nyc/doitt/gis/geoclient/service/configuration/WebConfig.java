/*
 * Copyright 2013-2019 the original author or authors.
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

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import gov.nyc.doitt.gis.geoclient.service.search.web.response.SearchResultConverter;
import gov.nyc.doitt.gis.geoclient.service.web.ViewHelper;

@Configuration
@ComponentScan(basePackages = { "gov.nyc.doitt.gis.geoclient.service",
        "gov.nyc.doitt.gis.geoclient.parser.configuration" })
public class WebConfig implements WebMvcConfigurer {
    private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

    @Autowired
    private Environment environment;

    // Autowired by Spring using the AppConfig#marshaller() method
    @Autowired
    private XStreamMarshaller marshaller;

    @PostConstruct
    public void logEnvironment() {
        log.info("Spring Environment: {}", environment);
    }

    /**
     * Set JSON as the default content type that gets returned
     *
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#configureContentNegotiation(org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer)
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        log.debug("[configureContentNegotiation]");
        configurer.defaultContentType(MediaType.APPLICATION_JSON).mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML).favorPathExtension(true);
    }

    /**
     * Enable default MVC dispatcher set-up
     *
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#configureDefaultServletHandling(org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer)
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        log.debug("[configureDefaultServletHandling]");
        configurer.enable();
    }

    /**
     * Added JSON and XML message converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.debug("[configureMessageConverters]");
        converters.add(jsonMessageConverter());
        converters.add(xmlMessageConverter());
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        log.debug("[configurePathMatch]");
        configurer.setUseSuffixPatternMatch(true).setUseTrailingSlashMatch(false)
                .setUseRegisteredSuffixPatternMatch(true);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.debug("[addFormatters]");
        registry.addConverter(searchResultConverter());
    }

    @Bean
    public SearchResultConverter searchResultConverter() {
        log.debug("[searchResultConverter]");
        return new SearchResultConverter();
    }

    @Bean
    public HttpMessageConverter<?> jsonMessageConverter() {
        log.debug("[jsonMessageConverter]");
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public HttpMessageConverter<?> xmlMessageConverter() {
        log.debug("[xmlMessageConverter]");
        return new MarshallingHttpMessageConverter(this.marshaller);
    }

    @Bean(name = "viewHelper")
    public ViewHelper viewHelper() {
        return new ViewHelper();
    }

    /*
     * TODO Fixme! I am insecure.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        log.debug("[addCorsMappings]");
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET");
    }

}

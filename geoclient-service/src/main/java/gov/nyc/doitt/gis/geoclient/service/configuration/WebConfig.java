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
package gov.nyc.doitt.gis.geoclient.service.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.converters.ConverterMatcher;

import org.slf4j.Logger;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.service.domain.BadRequest;
import gov.nyc.doitt.gis.geoclient.service.domain.FileInfo;
import gov.nyc.doitt.gis.geoclient.service.domain.Version;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.SearchResultConverter;
import gov.nyc.doitt.gis.geoclient.service.web.ViewHelper;
import gov.nyc.doitt.gis.geoclient.service.xstream.MapConverter;

/**
 * Customizes the default web configuration by implementing the Spring
 * {@link WebMvcConfigurer} interface.
 *
 * Some of the configuration is done to support legacy Spring MVC
 * content negotiation which is not generally a good idea.
 *
 * Once Geoclient doesn't have to support legacy functionality like
 * controller dispatch based on file type extensions, the legacy stuff
 * should be removed.
 *
 * NOTE: Spring Boot configures common Spring MVC defaults described in the
 * reference docs section "Spring MVC Auto-configuration". This happens
 * when a class has the @Configuration annotation and implements
 * WebMvcConfigurer.
 *
 * See https://www.baeldung.com/spring-mvc-content-negotiation-json-xml
*/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(WebConfig.class);

    /**
     * Implements part of the legacy content negotiation/path matching
     * strategy that will eventually be deprecated since it is insecure.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(searchResultConverter());
    }

    /**
     * Implements part of the legacy content negotiation/path matching
     * strategy that will eventually be deprecated since it is insecure.
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#configureContentNegotiation(org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer)
     */
    @Override
    @SuppressWarnings("deprecation")
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true).
        favorParameter(false).
        //parameterName("format").
        ignoreAcceptHeader(true).
        useJaf(false).
        defaultContentType(MediaType.APPLICATION_JSON).
        mediaType("xml", MediaType.APPLICATION_XML).
        mediaType("json", MediaType.APPLICATION_JSON);
    }

    /*
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#configureDefaultServletHandling(org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer)
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable("geoclientDispatcherServlet");
    }

    /**
     * Adds JSON and XML message converters.
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jsonMessageConverter());
        converters.add(xmlMessageConverter());
    }

    // Beans //

    @Bean
    public HttpMessageConverter<?> jsonMessageConverter() {
        //MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        //converter.getObjectMapper()
        //    .configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public XStreamMarshaller marshaller() {
        XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setConverters(new ConverterMatcher[] { new MapConverter() });
        Map<String, Class<?>> aliases = new HashMap<String, Class<?>>();
        aliases.put("geosupportResponse", Map.class);       // -> <geosupportResult class="tree-map">
        //aliases.put("geosupportResponse", TreeMap.class); // -> <geosupportResult class="geosupportResult">
        aliases.put("version", Version.class);
        aliases.put("fileInfo", FileInfo.class);
        aliases.put("error", BadRequest.class);
        aliases.put("chunk", Chunk.class);
        aliases.put("token", Token.class);
        marshaller.setAliases(aliases);
        marshaller.setAutodetectAnnotations(true);
        return marshaller;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(true);
        configurer.setPathMatcher(new AntPathMatcher());
    }

    @Bean
    public SearchResultConverter searchResultConverter() {
        return new SearchResultConverter();
    }

    @Bean(name = "viewHelper")
    public ViewHelper viewHelper() {
        return new ViewHelper();
    }

    @Bean
    public HttpMessageConverter<?> xmlMessageConverter() {
        return new MarshallingHttpMessageConverter(marshaller());
    }

    /**
     * Tomcat 8.5 before 8.5.44 and 9 before 9.0.23 do not have a certain API
     * that Spring assumes is there and so removing the
     * "tomcatWebServerFactoryCustomizer" is necessary to prevent a
     * NoSuchMethodError.
     *
     * However, since this bean isn't actually used when deploying a war file
     * to a container so it's safe to remove.
     *
     * Following an upgrade from spring-boot 2.7.5 to 2.7.11, this method
     * caused a NoSuchBeanDefinitionException in mock test
     * SingleFieldSearchControllerTest because this bean isn't there to begin
     * with.
     *
     * Adding a try/catch and ignoring the exception fixes the issue. Once
     * the affected versions of Tomcat are no longer in use, this method can
     * be removed.
     *
     * See https://github.com/spring-projects/spring-boot/issues/19308
     */
    @Bean
    public static BeanFactoryPostProcessor removeTomcatWebServerCustomizer() {
        return new BeanFactoryPostProcessor() {
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
                if (((DefaultListableBeanFactory)beanFactory).containsBeanDefinition("tomcatWebServerFactoryCustomizer")) {
                    ((DefaultListableBeanFactory)beanFactory).removeBeanDefinition("tomcatWebServerFactoryCustomizer");
                    LOGGER.info("Removed bean \"tomcatWebServerFactoryCustomizer\".");
                } else {
                    LOGGER.info("Bean \"tomcatWebServerFactoryCustomizer not found\": assuming recent version(s) of Tomcat/Spring Boot.");
                }
            }
        };
    }
}

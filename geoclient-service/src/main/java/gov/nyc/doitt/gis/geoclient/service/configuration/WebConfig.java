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
package gov.nyc.doitt.gis.geoclient.service.configuration;

//import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.converters.ConverterMatcher;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
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

// See https://www.baeldung.com/spring-mvc-content-negotiation-json-xml

// Spring Boot configures common Spring MVC defaults described in the
// reference docs section "Spring MVC Auto-configuration". This happens
// when a class has the @Configuration annotation and implements
// WebMvcConfigurer.
// To prevent Boot from configuring any defaults, add @EnableWebMvc.
// NOTE: A Spring MVC application can only have one class with this annotation.
// @EnableWebMvc
//
// Configuration of the ResourceHandlerRegistry and
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*
     * TODO Fixme! I am insecure.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(searchResultConverter());
    }

    //@Override
    //public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //    // Default strategy configured by Spring Boot is SimpleUrlHandlerMapping?:
    //    //   ResourceHttpRequestHandler
    //    //    [classpath [META-INF/resources/], classpath [resources/], classpath [static/], classpath [public/], ServletContext [/]]
    //    registry.addResourceHandler("/resources/**")
    //            .addResourceLocations("classpath:/static/")
    //            .setCacheControl(CacheControl.maxAge(Duration.ofDays(365)));
    //}

    /*
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
     * Added JSON and XML message converters
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

    @Bean
    public static BeanFactoryPostProcessor removeTomcatWebServerCustomizer() {
        return (beanFactory) ->
            ((DefaultListableBeanFactory)beanFactory).removeBeanDefinition("tomcatWebServerFactoryCustomizer");
    }
}

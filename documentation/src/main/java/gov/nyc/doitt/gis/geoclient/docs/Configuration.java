package gov.nyc.doitt.gis.geoclient.docs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@org.springframework.context.annotation.Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ExternalProperties.class)
public class Configuration {

    @Bean(name = "externalProperties")
    @ConfigurationProperties
    public ExternalProperties externalProperties() {
        return new ExternalProperties();
    }

    @Bean
    public RestClient restClient() {
        return RestClient.create(externalProperties().getBaseUrl());
    }

    @Bean
    public GeneratorService generatorService () {
        return new GeneratorService(restClient(), externalProperties().getOutputDir());
    }
}

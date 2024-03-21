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
package gov.nyc.doitt.gis.geoclient.docs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ExternalProperties.class)
public class Configuration {

    @Autowired
    private ExternalProperties externalProperties;

    @Bean
    public ServiceClient serviceClient() {
        return new ServiceClient(externalProperties.getBaseUrl());
    }

    @Bean
    public GeneratorService generatorService () {
        return new GeneratorService(serviceClient(), responseWriter());
    }

    @Bean
    public ResponseWriter responseWriter() {
        return new ResponseWriter(externalProperties.getOutputDir());
    }
}

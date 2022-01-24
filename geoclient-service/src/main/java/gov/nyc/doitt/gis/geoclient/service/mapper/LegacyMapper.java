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
package gov.nyc.doitt.gis.geoclient.service.mapper;

import com.github.dozermapper.core.MapperModelContext;
import com.github.dozermapper.core.MappingException;
import com.github.dozermapper.core.metadata.MappingMetadata;

public class LegacyMapper implements com.github.dozermapper.core.Mapper {

    private final com.github.dozermapper.core.Mapper target;

    public LegacyMapper(com.github.dozermapper.core.Mapper target) {
        super();
        this.target = target;
    }

    public <T> T map(Object source, Class<T> destinationClass) throws MappingException {
        return target.map(source, destinationClass);
    }

    public void map(Object source, Object destination) throws MappingException {
        target.map(source, destination);
    }

    public int hashCode() {
        return target.hashCode();
    }

    public <T> T map(Object source, Class<T> destinationClass, String mapId) throws MappingException {
        return target.map(source, destinationClass, mapId);
    }

    public void map(Object source, Object destination, String mapId) throws MappingException {
        target.map(source, destination, mapId);
    }

    public MappingMetadata getMappingMetadata() {
        return target.getMappingMetadata();
    }

    public MapperModelContext getMapperModelContext() {
        return target.getMapperModelContext();
    }

    public boolean equals(Object obj) {
        return target.equals(obj);
    }

    public String toString() {
        return target.toString();
    }

}

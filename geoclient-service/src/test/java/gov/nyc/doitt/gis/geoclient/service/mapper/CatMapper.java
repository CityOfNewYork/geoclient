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

import java.util.Map;

class CatMapper extends AbstractParameterMapper<Cat> {

    @Override
    public Cat fromParameters(Map<String, Object> source, Cat destination) throws MappingException {
        String name = (String) source.get("name");
        destination.setName(name);
        return destination;
    }

    @Override
    public Map<String, Object> toParameters(Cat source, Map<String, Object> destination) throws MappingException {
        destination.put("name", source.getName());
        return destination;
    }

}
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
package gov.nyc.doitt.gis.geoclient.service.street.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import gov.nyc.doitt.gis.geoclient.api.StreetCode;

/**
 * Converts between string representations of street codes in request parameters
 * to {@link StreetCode} arguments.
 *
 * @author mlipper
 * @since 2.0
 */
public class StreetCodeConverter implements Converter<String, StreetCode> {

    @Override
    public StreetCode convert(@NonNull String source) {
        return null;
    }

}

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
package gov.nyc.doitt.gis.geoclient.service.invoker;

import java.util.Map;

import gov.nyc.doitt.gis.geoclient.service.domain.FieldSet;

/**
 * Selectively converts field values returned by specific Geosupport functions.
 * Relieves callers of the responsibility for knowing whether conversions are
 * required.
 *
 * Implmentations are typically configured {@link FieldSet}s to determine if
 * conversions are necessary and, if so, to what fields they should apply.
 *
 * @author Matthew Lipper
 * @since 2.0
 * @see FieldSet
 */
public interface FieldSetConverter {
    /**
     * Converts/formats specific values in the given arguments parameter if
     * configured with a matching function identifier and field names found in that
     * parameter's key set. Ignores partial or total mismatches.
     *
     * @param functionId Function identifier which cannot be null
     * @param arguments Field name (key) and value pairs of Geosupport call results
     */
    void convert(String functionId, Map<String, Object> arguments);
}
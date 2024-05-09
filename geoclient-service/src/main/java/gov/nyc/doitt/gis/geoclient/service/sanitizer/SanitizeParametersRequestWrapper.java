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
package gov.nyc.doitt.gis.geoclient.service.sanitizer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.parser.util.TextUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * Implementation of {@code HttpServletRequestWrapper} adapted from <a href="https://www.baeldung.com/java-servlet-request-set-parameter">this article</a>
 * about sanitizing request parameters.
 *
 * @author mlipper
 * @since 2.0
 * @see <a href="https://github.com/eugenp/tutorials/blob/master/web-modules/javax-servlets-2/src/main/java/com/baeldung/setparam/SanitizeParametersRequestWrapper.java">SanitizeParametersFilter</a>
 */
public class SanitizeParametersRequestWrapper extends HttpServletRequestWrapper {

    static final Logger logger = LoggerFactory.getLogger(SanitizeParametersRequestWrapper.class);

    private final Map<String, String[]> sanitizedMap;

    public SanitizeParametersRequestWrapper(HttpServletRequest request) {
        super(request);
        logger.info("Sanitizing request {}.", request.getRequestId());
        sanitizedMap = Collections.unmodifiableMap(
                request.getParameterMap().entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> Arrays.stream(entry.getValue())
                                        .map(TextUtils::sanitize)
                                        .toArray(String[]::new))));

        logger.info("Request {} sanitized.", request.getRequestId());
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return sanitizedMap;
    }

    @Override
    public String[] getParameterValues(String name) {
        return Optional.ofNullable(getParameterMap().get(name))
                .map(values -> Arrays.copyOf(values, values.length))
                .orElse(null);
    }

    @Override
    public String getParameter(String name) {
        return Optional.ofNullable(getParameterValues(name))
                .map(values -> values[0])
                .orElse(null);
    }
}

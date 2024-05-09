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

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Adapter for using the Spring Framework web API for sanitizing request
 * parameters using a {@link jakarta.servlet.Filter}.
 *
 * @author mlipper
 * @since 2.0
 * @see org.springframework.web.filter.GenericFilterBean
 */
@Component
public class SanitizeParametersFilter extends GenericFilterBean {
    static final Logger logger = LoggerFactory.getLogger(SanitizeParametersFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("Filtering request {} to sanitize request parameters.", request.getRequestId());
        HttpServletRequest httpReq = (HttpServletRequest) request;
        chain.doFilter(new SanitizeParametersRequestWrapper(httpReq), response);
        logger.info("Request {} sanitization filter complete.", request.getRequestId());
    }
}

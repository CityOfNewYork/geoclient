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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// See https://github.com/spring-projects/spring-framework/blob/main/spring-test/src/test/java/org/springframework/test/web/servlet/samples/standalone/FilterTests.java
public class SanitizeParametersRequestWrapperTest {
    private static final String CLEAN_VALUE = "120 broadway";
    private static final String DIRTY_VALUE = " \r 120\r\nbroadway \n ";
    private static final String PARAM_NAME = "input";
    private static final String URI = "/locate";

    private MockHttpServletRequest mockHttpRequest;

    @BeforeEach
    void setUp() {
        this.mockHttpRequest = new MockHttpServletRequest();
        this.mockHttpRequest.setRequestURI(URI);
        this.mockHttpRequest.setParameter(PARAM_NAME, DIRTY_VALUE);
    }

    @Test
    void testGetParameter() {
        SanitizeParametersRequestWrapper wrapper = new SanitizeParametersRequestWrapper(mockHttpRequest);
        assertEquals(CLEAN_VALUE, wrapper.getParameter(PARAM_NAME));
    }

    @Test
    void testGetParameterMap() {
        SanitizeParametersRequestWrapper wrapper = new SanitizeParametersRequestWrapper(mockHttpRequest);
        String[] result = wrapper.getParameterMap().getOrDefault(PARAM_NAME, null);
        assertEquals(CLEAN_VALUE, result[0]);
    }

    @Test
    void testGetParameterValues() throws Exception {
        standaloneSetup(new TestRestController()).addFilter(new SanitizeParametersFilter()).build().perform(
            get(URI).queryParam(PARAM_NAME, DIRTY_VALUE)).andExpect(content().string(CLEAN_VALUE));
    }

    @Controller
    private static class TestRestController {
        @GetMapping(URI)
        @ResponseBody
        public String echo(@RequestParam String input) {
            return input;
        }
    }
}

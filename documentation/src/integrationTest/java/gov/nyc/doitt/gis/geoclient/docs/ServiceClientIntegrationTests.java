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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties(ExternalProperties.class)
public class ServiceClientIntegrationTests {

	private static final Pattern URI_WITHOUT_QUERY_PATTERN = Pattern.compile("([^?]+)(.*)");
	private static final Pattern QUERY_PARAM_PATTERN = Pattern.compile("([^&=]+)(=?)([^&]+)?");

    @Autowired
    private ServiceClient client;
    @Autowired
    private ExternalProperties props;

    @BeforeEach
    public void setUp() {
        assertNotNull(this.client);
    }

    @Test
    public void testBuildUri_search() {
        Sample sample = new Sample.Builder("1", PathVariable.SEARCH, "search test").withQueryString("input", "120 Broadway").build();
        URI uri = this.client.buildUri(sample);
        assertEquals(props.getBaseUrl() + "/search?input=120%20Broadway", uri.toString());
    }

    @Test
    public void testBuildUri_address() throws Exception {
        Sample sample = new Sample.Builder("1", PathVariable.ADDRESS, "search test").withQueryString("houseNumber", "120", "street", "Broadway", "borough", "Manhattan").build();
        Map<String,String> expectedQueryMap = sample.getQueryString();
        URI uri = this.client.buildUri(sample);
        Map<String, String> actualQueryMap = queryStringToMap(uri.getQuery());
        assertEquals(expectedQueryMap, actualQueryMap);
        String actualUriWithoutQueryString = uriWithoutQueryString(uri.toString());
        assertEquals(props.getBaseUrl() + "/address", actualUriWithoutQueryString);
    }

    private Map<String, String> queryStringToMap(String query) {
        Map<String, String> result = new HashMap<>();
		if (query != null) {
			Matcher matcher = QUERY_PARAM_PATTERN.matcher(query);
			while (matcher.find()) {
				String name = matcher.group(1);
				String value = matcher.group(3);
                result.put(name, value);
			}
		}
        return result;
    }

    private String uriWithoutQueryString(String uriString) {
        String result = null;
		if (uriString != null) {
			Matcher matcher = URI_WITHOUT_QUERY_PATTERN.matcher(uriString);
			while (matcher.find()) {
				result = matcher.group(1);
			}
		}
        return result;
    }
}
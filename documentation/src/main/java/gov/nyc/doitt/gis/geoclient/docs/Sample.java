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

import java.util.HashMap;
import java.util.Map;

public class Sample {
    public static class Builder {
        private String id;
        private String description;
        private PathVariable pathVariable;
        private Map<String, String> queryString = new HashMap<>();
        public Builder(String id, PathVariable pathVariable, String description) {
            this.id = id;
            this.pathVariable = pathVariable;
            this.description = description;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder pathVariable(PathVariable pathVariable) {
            this.pathVariable = pathVariable;
            return this;
        }

        public Builder pathVariable(String pathVariable) {
            this.pathVariable = PathVariable.fromString(pathVariable);
            return this;
        }

        public Builder queryParam(String name, String value) {
            this.queryString.put(name, value);
            return this;
        }

        public Builder withQueryString(String... strings) {
            int len = strings.length;
            if (len == 0 || len % 2 != 0) {
                throw new IllegalArgumentException(
                    String.format("An even number of two or more arguments is required: %d given.", len));
            }
            for (int i = 0; i < strings.length; i = i + 2) {
                this.queryString.put(strings[i], strings[i + 1]);
            }
            return this;
        }

        public Sample build() {
            return new Sample(this);
        }
    }

    private String id;
    private String description;
    private String pathVariable;
    private Map<String, String> queryString;
    private String request;
    private String response;

    private Sample(Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.pathVariable = builder.pathVariable.toString();
        this.queryString = builder.queryString;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the pathParameter
     */
    public String getPathVariable() {
        return pathVariable;
    }

    /**
     * @return the queryString
     */
    public Map<String, String> getQueryString() {
        return queryString;
    }

    /**
     * @return the request
     */
    public String getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(String request) {
        this.request = request;
    }

    /**
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        //return "Sample [id=" + id + ", description=" + description + ", pathParameter=" + pathParameter
        //        + ", queryString=" + queryString + ", request=" + request + ", response=" + response + "]";
        return "sample " + id + ". " + pathVariable + " - " + description;

    }

}

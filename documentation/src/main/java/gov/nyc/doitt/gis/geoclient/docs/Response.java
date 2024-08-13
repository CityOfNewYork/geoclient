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

import java.net.URI;

import org.springframework.http.HttpStatusCode;

public class Response {
    private final URI uri;
    private final HttpStatusCode status;
    private final String body;

    public Response(URI uri, HttpStatusCode status, String body) {
        this.uri = uri;
        this.status = status;
        this.body = body;
    }

    public String getUri() {
        return this.uri.toString();
    }

    public String getStatus() {
        return status.toString();
    }

    public String getBody() {
        return body;
    }

    public boolean isSuccess() {
        return this.status.is2xxSuccessful();
    }

    @Override
    public String toString() {
        return "Response [uri=" + uri + ", status=" + status + "]";
    }

}

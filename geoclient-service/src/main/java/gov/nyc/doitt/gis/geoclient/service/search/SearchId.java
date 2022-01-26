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
package gov.nyc.doitt.gis.geoclient.service.search;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class SearchId
{
    private final String prefix;
    private final AtomicLong sequence;

    public SearchId(String prefix)
    {
        super();
        this.prefix = prefix;
        this.sequence = new AtomicLong(0);
    }

    public String next()
    {
        return String.format("%s-%d-%d", prefix, sequence.incrementAndGet(), new Date().getTime());
    }
}

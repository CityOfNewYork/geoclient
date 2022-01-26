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
package gov.nyc.doitt.gis.geoclient.function;

import java.nio.ByteBuffer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.jni.Geoclient;

public class GeosupportFunction implements Function
{
    private static final Logger log = LoggerFactory.getLogger(GeosupportFunction.class);
    //private static final Logger JNI_BUFFER_LOGGER = LoggerFactory.getLogger("GC_JNI_BUFFER_LOGGER");
    private final String id;
    private final WorkArea workAreaOne;
    private final WorkArea workAreaTwo;
    private final Geoclient geoclient;
    private final Configuration configuration;

    public GeosupportFunction(String id, WorkArea workAreaOne, WorkArea workAreaTwo, Geoclient geoclient, Configuration configuration)
    {
        super();
        this.id = id;
        this.workAreaOne = workAreaOne;
        this.workAreaTwo = workAreaTwo;
        this.geoclient = geoclient;
        this.configuration = configuration;
    }

    public GeosupportFunction(String id, WorkArea workAreaOne, WorkArea workAreaTwo, Geoclient geoclient)
    {
        this(id, workAreaOne, workAreaTwo, geoclient, null);
    }

    public GeosupportFunction(String id, WorkArea workAreaOne, Geoclient geoclient)
    {
        this(id, workAreaOne, null, geoclient);
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.nyc.doitt.gis.geoclient.function.Function#call(java.util.Map)
     */
    @Override
    public Map<String, Object> call(Map<String, Object> parameters)
    {
        if (isTwoWorkAreas())
        {
            return doTwoWorkAreaCall(parameters);
        }
        return doOneWorkAreaCall(parameters);
    }

    public String getId()
    {
        return id;
    }

    public WorkArea getWorkAreaOne()
    {
        return workAreaOne;
    }

    public WorkArea getWorkAreaTwo()
    {
        return workAreaTwo;
    }

    @Override
    public String toString()
    {
        return "Function [id=" + id + "]";
    }

    private Map<String, Object> doOneWorkAreaCall(Map<String, Object> parameters)
    {
        ByteBuffer wa1 = workAreaOne.createBuffer(parameters);
        logFunctionCall();
        logBuffer("WA1", "input", wa1);
        this.geoclient.callgeo(wa1, null);
        logBuffer("WA1", "output", wa1);
        return this.workAreaOne.parseResults(wa1);
    }

    private Map<String, Object> doTwoWorkAreaCall(Map<String, Object> parameters)
    {
        ByteBuffer wa1 = workAreaOne.createBuffer(parameters);
        ByteBuffer wa2 = workAreaTwo.createBuffer();
        logFunctionCall();
        logBuffer("WA1", "input", wa1);
        this.geoclient.callgeo(wa1, wa2);
        logBuffer("WA1", "output", wa1);
        logBuffer("WA2", "output", wa2);
        Map<String, Object> result = this.workAreaOne.parseResults(wa1);
        result.putAll(workAreaTwo.parseResults(wa2));
        return result;
    }

    public boolean isTwoWorkAreas()
    {
        return this.workAreaTwo != null;
    }

    public Configuration getConfiguration()
    {
        return configuration;
    }

    private void logFunctionCall()
    {
        log.debug("Calling {}", this);
    }

    private void logBuffer(String workArea, String inputOutput, ByteBuffer buffer)
    {
        final String bufferString = new String(buffer.array());
        log.trace("{}[{}]:'{}'", String.format("F%6s", this.id + "." + workArea), String.format("%6s", inputOutput), bufferString);
        //JNI_BUFFER_LOGGER.trace("{}[{}]:'{}'", String.format("F%6s", this.id + "." + workArea), String.format("%6s", inputOutput), bufferString);
    }

}

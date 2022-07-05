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
package gov.nyc.doitt.gis.geoclient.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;

public class Registry
{
    private static final Logger log = LoggerFactory.getLogger(Registry.class);

    private static final ConcurrentMap<String, Function> functionRegistry = new ConcurrentHashMap<String, Function>();
    private static final ConcurrentMap<String, WorkArea> workAreaRegistry = new ConcurrentHashMap<String, WorkArea>();

    private Registry()
    {
    }

    public static void addFunction(Function function)
    {
        log.debug("add({})", function);
        functionRegistry.putIfAbsent(function.getId(), function);
    }

    public static void addWorkArea(WorkArea workArea)
    {
        log.debug("add({})", workArea);
        workAreaRegistry.putIfAbsent(workArea.getId(), workArea);
    }

    public static void clearAll()
    {
        clearFunctions();
        clearWorkAreas();
    }

    public static void clearFunctions()
    {
        functionRegistry.clear();
        log.debug("functionRegistry.clear()");
    }

    public static void clearWorkAreas()
    {
        workAreaRegistry.clear();
        log.debug("workAreaRegistry.clear()");
    }

    public static boolean containsFunction(String id)
    {
        boolean contains = functionRegistry.containsKey(id);
        log.debug("functionRegistry.containsKey({})=={}", id, contains);
        return contains;
    }

    public static boolean containsWorkArea(String name)
    {
        boolean contains = workAreaRegistry.containsKey(name);
        log.debug("workAreaRegistry.containsKey({})=={}", name, contains);
        return contains;
    }

    public static Function getFunction(String id)
    {
        Function function = functionRegistry.get(id);
        log.debug("functionRegistry.get({})=={}", id, function);
        return function;
    }

    public static WorkArea getWorkArea(String name)
    {
        WorkArea workArea = workAreaRegistry.get(name);
        log.debug("workAreaRegistry.get({})=={}", name, workArea);
        return workArea;
    }

}

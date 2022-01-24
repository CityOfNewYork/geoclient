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
package gov.nyc.doitt.gis.geoclient.test;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.event.Level;

public class LogLevelAdapter {

    public static <K, V> void logAll(Level level, Logger logger, Map<K, V> map) {
        map.entrySet().forEach(entry -> logAtLevel(level, logger, entry));
    }

    public static void logAll(Level level, Logger logger, Properties properties) {
        properties.entrySet().forEach(entry -> logAtLevel(level, logger, entry));
    }

    private static <K, V> void logAtLevel(Level level, Logger logger, Entry<K, V> e) {
        String fmt = "{}={}";
        BiConsumer<String, Object[]> log = null;
        if (Level.ERROR.equals(level)) {
            log = logger::error;
        } else if (Level.WARN.equals(level)) {
            log = logger::warn;
        } else if (Level.INFO.equals(level)) {
            log = logger::info;
        } else if (Level.DEBUG.equals(level)) {
            log = logger::debug;
        } else if (Level.TRACE.equals(level)) {
            log = logger::trace;
        } else {
            // System.out.println(String.format("%s=%s",e.getKey(),e.getValue()));
            // NO-OP
            ;
        }
        if(log != null) {
            log.accept(fmt, new Object[] {e.getKey(), e.getValue()});
        }
    }

}

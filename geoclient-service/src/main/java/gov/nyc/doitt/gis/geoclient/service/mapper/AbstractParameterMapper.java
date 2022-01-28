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
package gov.nyc.doitt.gis.geoclient.service.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractParameterMapper<T> implements Mapper<T> {

    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractParameterMapper.class);

    @Override
    public T fromParameters(Map<String, Object> source, Class<T> destinationClass) throws MappingException {
        return fromParameters(source, newInstance(destinationClass));
    }

    @Override
    public abstract T fromParameters(Map<String, Object> source, T destination) throws MappingException;

    @Override
    public Map<String, Object> toParameters(T source) throws MappingException {
        return toParameters(source, new HashMap<>());
    }

    @Override
    public abstract Map<String, Object> toParameters(T source, Map<String, Object> destination)
            throws MappingException;

    @SuppressWarnings("unchecked")
    protected T newInstance(Class<T> clazz) throws MappingException {
        try {

            Constructor<?>[] ctors = clazz.getDeclaredConstructors();
            Constructor<?> constructor = null;
            for (Constructor<?> ctor : ctors) {
                if(ctor.getGenericExceptionTypes().length == 0) {
                    ctor.setAccessible(true);
                    constructor = ctor;
                    break;
                }
            }
            return (T)constructor.newInstance();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            String className = clazz.getCanonicalName();
            String msg = String.format("Error creating instance of class %s using Class<%s>#newInstance()", className,
                    clazz.getSimpleName());
            LOGGER.error(e.getMessage());
            throw new MappingException(msg, e);
        }
    }
}
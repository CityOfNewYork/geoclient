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
package gov.nyc.doitt.gis.geoclient.service.xstream;

import java.util.Collection;
import java.util.Map;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class MapConverter implements Converter {

    public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
        return Map.class.isAssignableFrom(type);
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) source;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            writer.startNode(entry.getKey());
            Object value = entry.getValue();
            if (value instanceof Map) {
                // Recursively map any sub-Maps
                this.marshal(value, writer, context);
            } else if (value instanceof Collection<?>) {
                context.convertAnother(value);
            } else {
                writer.setValue(value != null ? entry.getValue().toString() : null);
            }
            writer.endNode();
        }
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        throw new UnsupportedOperationException("Unmarshalling is not implemented.");
    }

}

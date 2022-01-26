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
package gov.nyc.doitt.gis.geoclient.config.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.extended.ToStringConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import com.thoughtworks.xstream.security.TypePermission;

/**
 * Creates an {@link XStream} instance from a limited subset of XStream
 * configuration options.
 * <p>
 * This class is not meant as a general purpose XStream builder; rather,
 * configuration options are limited to only what's needed for unmarshalling XML
 * into Geoclient configuration objects.
 * <p>
 * <b>NOTE:</b> This class is <b>not</b> thread-safe.
 *
 * @author mlipper
 * @since 2.0
 *
 * @see XStream's
 *      <a href="http://x-stream.github.io/security.html#example">example</a>
 *      page
 *
 */
/**
 * @author mlipper
 *
 */
public class XStreamBuilder
{
    private XStream xstream;
    private List<Package> packages;

    /**
     * Creates a new builder using a {@code DomDriver} and configures default
     * permissions.
     *
     * @see DomDriver
     */
    public XStreamBuilder()
    {
        this(new DomDriver());
    }

    /**
     * Creates a new builder using a {@link HierarchicalStreamDriver} and
     * configures default permissions by calling
     * {@link #addDefaultPermissions()}.
     *
     * @param driver {@code HierarchicalStreamDriver} instance to use
     */
    public XStreamBuilder(HierarchicalStreamDriver driver)
    {
        this.xstream = new XStream(driver);
        this.packages = new ArrayList<>();
        // For now, make this call automatically. If this builder needs to be
        // more flexible, this call can be removed and the method can become
        // public.
        addDefaultPermissions();
    }

    /**
     * Uses the configured package names, sets XStream permissions allowing
     * deserialization of all associated classes.
     *
     * @return {@code XStream} instance
     */
    public XStream build()
    {
        for (Package p : packages)
        {
            xstream.allowTypesByWildcard(new String[] { p.getName() + ".*" });
        }
        return this.xstream;
    }

    /**
     * Adds a Java package whose name will be used to construct a glob
     * expression matching all contained classes. This expression will be added
     * to the list of those evaluated when the {@link #build()} method is called
     * to white list XStream targets.
     *
     * @param p Java {@code java.lang.Package}
     * @return {@code XStreamBuilder} instance
     */
    public XStreamBuilder addPackage(Package p)
    {
        this.packages.add(p);
        return this;
    }

    /**
     * Uses the {@link Package} of the given  argument to call
     * {@link #addPackage(Package)}.
     *
     * @param type {@code Class} whose {@code java.lang.Package} will be used to call {@link #addPackage(Package)}
     * @return {@code XStreamBuilder} instance
     * @see #addPackage(Package)
     */
    public XStreamBuilder addAllClassesInSamePackageAs(Class<?> type)
    {
        return this.addPackage(type.getPackage());
    }

    /**
     * Wraps call to {@link XStream#alias(String, Class)}.
     *
     * @param name of alias to use
     * @param type {@code java.lang.Class} to be aliased
     * @return {@code XStreamBuilder} instance
     */
    public XStreamBuilder alias(String name, Class<?> type) {
        xstream.alias(name, type);
        return this;
    }

    /**
     * Wraps call to {@link XStream#aliasAttribute(Class, String, String)}.
     *
     * @param definedIn {@code java.lang.Class} containing the attribute
     * @param attributeName name {@code java.lang.String}
     * @param alias alias name {@code java.lang.String}
     * @return {@code XStreamBuilder}
     */
    public XStreamBuilder aliasAttribute(Class<?> definedIn, String attributeName, String alias) {
        xstream.aliasAttribute(definedIn, attributeName, alias);
        return this;
    }

    /**
     * Wraps call to {@link XStream#setMode(int)} with argument {@link XStream#ID_REFERENCES}.
     * @return {@code XStreamBuilder}
     */
    public XStreamBuilder setReferenceMode() {
        // Use reference="another id" to reference elements
        xstream.setMode(XStream.ID_REFERENCES);
        return this;
    }

    /**
     * Wraps call to {@link XStream#registerConverter(Converter)}.
     * @param converter to register
     * @return {@code XStreamBuilder}
     */
    public XStreamBuilder registerConverter(Converter converter) {
        xstream.registerConverter(converter);
        return this;
    }

    /**
     * Wraps call to {@link XStream#registerConverter(SingleValueConverter)}.
     * @param converter XStream converter instance
     * @return {@code XStreamBuilder}
     */
    public XStreamBuilder registerConverter(SingleValueConverter converter) {
        xstream.registerConverter(converter);
        return this;
    }

    /**
     * Registers the given {@link Class} argument as a {@link ToStringConverter} with {@link XStream}.
     *
     * @param type {@code java.lang.Class} to register
     * @return {@code XStreamBuilder}
     */
    public XStreamBuilder registerToStringConverter(Class<?> type) {
        try
        {
            registerConverter(new ToStringConverter(type));
        } catch (NoSuchMethodException e)
        {
            throw new XmlConfigurationException("Could not create ToStringConverter for class "
                    + type.getCanonicalName() + ": " + e.getMessage());
        }
        return this;
    }

    /**
     * Wraps call to {@link XStream#aliasField(String, Class, String)}.
     * @param alias name of alias
     * @param definedIn type containing aliased field
     * @param fieldName name of aliased target field
     * @return {@code XStreamBuilder}
     */
    public XStreamBuilder aliasField(String alias, Class<?> definedIn, String fieldName) {
        xstream.aliasField(alias, definedIn, fieldName);
        return this;
    }

    /**
     * Wraps call to {@link XStream#addImplicitCollection(Class, String, Class)}.
     * @param ownerType {@code Class} containing the {@code Collection}
     * @param fieldName name of the field
     * @param itemType {@code Class} of the field's contents
     * @return {@code XStreamBuilder}
     */
    public XStreamBuilder addImplicitCollection(Class<?> ownerType, String fieldName, Class<?> itemType) {
        xstream.addImplicitCollection(ownerType, fieldName, itemType);
        return this;
    }

    /**
     * Adds the following XStream {@link TypePermission}s as sensible defaults:
     *
     * <ul>
     * <li>{@code NoTypePermission.NONE}</li>
     * <li>{@code NullPermission.NULL}</li>
     * <li>{@code PrimitiveTypePermission.PRIMITIVES}</li>
     * <li>Creates a {@code TypeHierarchyPermission} for all implementations of
     * the Collection.class</li>
     * </ul>
     *
     * Note that {@code NoTypePermission.NONE} is added first which will clear
     * out all existing permissions.
     *
     * Logic is inspired by on this
     * <a href="http://x-stream.github.io/security.html#example">example</a> on
     * the XStream site.
     *
     * @return {@link XStreamBuilder}
     */
    protected XStreamBuilder addDefaultPermissions()
    {
        // Clear out any existing permissions and reset as follows
        xstream.addPermission(NoTypePermission.NONE);
        xstream.addPermission(NullPermission.NULL);
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xstream.allowTypeHierarchy(Collection.class);
        xstream.allowTypes(new Class<?>[] { String.class });

        // If the above proves too restrictive and we are only reading streams
        // from internal sources
        // then the following will also clear out existing permission and then
        // white list all classes:
        // xStream.addPermission(AnyTypePermission.ANY);

        return this;
    }

}

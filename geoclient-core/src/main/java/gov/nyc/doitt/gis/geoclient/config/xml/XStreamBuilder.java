package gov.nyc.doitt.gis.geoclient.config.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.XStream;
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
public class XStreamBuilder
{
    private XStream xstream;
    private List<Package> packages;

    /**
     * Creates a new builder using a {@code DomDriver} and configures default
     * permissions.
     * 
     * @see {@link DomDriver}
     * @see {@link XStreamBuilder#XStreamBuilder(HierarchicalStreamDriver)
     */
    public XStreamBuilder()
    {
        this(new DomDriver());
    }

    /**
     * Creates a new builder using a {@code HierarchicalStreamDriver} and
     * configures default permissions by calling
     * {@link #addDefaultPermissions()}.
     * 
     * @see {@link HierarchicalStreamDriver}
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
     * @param p
     * @return {@link XStreamBuilder}
     */
    public XStreamBuilder addPackage(Package p)
    {
        this.packages.add(p);
        return this;
    }

    /**
     * Uses the {@link Package} of the given {@link Class} argument to call
     * {@link #addPackage(Package)}.
     * 
     * @param type
     * @return {@link XStreamBuilder}
     * @see #addPackage(Package)
     */
    public XStreamBuilder addAllClassesInSamePackageAs(Class<?> type)
    {
        return this.addPackage(type.getPackage());
    }
    
    public XStreamBuilder alias(String name, Class<?> type) {
        xstream.alias(name, type);
        return this;
    }

    public XStreamBuilder aliasAttribute(Class<?> definedIn, String attributeName, String alias) {
        xstream.aliasAttribute(definedIn, attributeName, alias);
        return this;
    }

    public XStreamBuilder setReferenceMode() {
        // Use reference="another id" to reference elements
        xstream.setMode(XStream.ID_REFERENCES);
        return this;
    }
    
    /**
     * Adds the following XStream {@link TypePermission}s as sensible defaults:
     * 
     * <ul>
     * <li>{@link NoTypePermission.NONE}</li>
     * <li>{@link NullPermission.NULL}</li>
     * <li>{@link PrimitiveTypePermission.PRIMITIVES}</li>
     * <li>Creates a {@code TypeHierarchyPermission} for all implementations of
     * the Collection.class</li>
     * </ul>
     * 
     * Note that {@link NoTypePermission.NONE} is added first which will clear
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

        // If the above proves too restrictive and we are only reading streams
        // from internal sources
        // then the following will also clear out existing permission and then
        // white list all classes:
        // xStream.addPermission(AnyTypePermission.ANY);

        return this;
    }

}

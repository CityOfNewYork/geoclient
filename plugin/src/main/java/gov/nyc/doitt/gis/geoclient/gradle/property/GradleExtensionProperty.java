package gov.nyc.doitt.gis.geoclient.gradle.property;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

public class GradleExtensionProperty extends Configuration {

    public GradleExtensionProperty(String id, Source source, Optional<?> value) {
	super(id, source, value);
    }

    public GradleExtensionProperty(String id, Source source, Object value) {
	super(id, source, value);
    }

    public GradleExtensionProperty(String id, Source source, String value) {
	super(id, source, value);
    }

    public GradleExtensionProperty(String id, Source source) {
	super(id, source, Optional.empty());
    }

    public Optional<?> getValue(Object instance, Method method, List<ValueSource> valueSources) throws RuntimeException {
	try {
	    method.setAccessible(true);
	    return Optional.ofNullable(method.invoke(instance));
	} catch (InvocationTargetException x) {
	    x.printStackTrace();
	    throw new RuntimeException(x);
	} catch (IllegalAccessException x) {
	    x.printStackTrace();
	    throw new RuntimeException(x);
	}
    }

}

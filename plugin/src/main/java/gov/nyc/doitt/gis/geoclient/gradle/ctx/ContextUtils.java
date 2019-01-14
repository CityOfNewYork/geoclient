package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import gov.nyc.doitt.gis.geoclient.gradle.property.Configuration;
import gov.nyc.doitt.gis.geoclient.gradle.property.EnvironmentVariable;
import gov.nyc.doitt.gis.geoclient.gradle.property.GradleProjectProperty;
import gov.nyc.doitt.gis.geoclient.gradle.property.Source;
import gov.nyc.doitt.gis.geoclient.gradle.property.SystemProperty;
import gov.nyc.doitt.gis.geoclient.gradle.property.ValueSource;

public class ContextUtils {

    public static List<Configuration> extractConfigurations(Object instance, Method method, List<ValueSource> valueSources) {
	Objects.requireNonNull(instance, "instance argument cannot be null");
	Objects.requireNonNull(method, "method argument cannot be null");
	Objects.requireNonNull(valueSources, "ValueSources argument cannot be null");
	List<Configuration> result = new ArrayList<>(valueSources.size());
	for (ValueSource vs : valueSources) {
	    String key = vs.key();
	    Source source = vs.value();
	    switch (source) {
	    case SYSTEM_PROPERTY:
		result.add(new SystemProperty(key, source));
		break;
	    case GRADLE_PROPERTY:
		result.add(new GradleProjectProperty(key, source));
		break;
	    case ENVIRONMENT_VARIABLE:
		result.add(new EnvironmentVariable(key, source));
		break;
	    case EXTENSION_PROPERTY:
		// TODO implement
		break;
	    case EXTENSION_DEFAULT:
		// TODO implement
		break;

	    default:
		break;
	    }
	}
	return result;
    }
    
    public static Map<Method, List<ValueSource>> getValueSources(Object instance) {
	Objects.requireNonNull(instance, "instance argument cannot be null");
	Method[] meths = instance.getClass().getDeclaredMethods();
	Map<Method, List<ValueSource>> result = new HashMap<>();
	for (int i = 0; i < meths.length; i++) {
	    Method method = meths[i];
	    if (Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers())) {
		if (method.getParameterTypes().length == 0) {
		    ValueSource[] ps = method.getAnnotationsByType(ValueSource.class);
		    if (ps != null && ps.length > 0) {
			result.put(method, Arrays.asList(ps));
		    }
		}
	    }
	}
	return result;
    }

    private ContextUtils() {
	/* no-op */
    }
}

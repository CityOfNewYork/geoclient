package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import static gov.nyc.doitt.gis.geoclient.gradle.configuration.Source.EXTENSION_PROPERTY;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import gov.nyc.doitt.gis.geoclient.gradle.configuration.Configuration;

public class ExtensionProperty extends Configuration {

    private final Object instance;
    private final Method method;

    public ExtensionProperty(Object instance, Method method) {
        super(concat(instance, method), EXTENSION_PROPERTY);
        this.instance = instance;
        this.method = method;
    }

    private static String concat(Object instance, Method method) {
        return String.format("%s#%s", instance.getClass().getName(), method.getName());
    }

    public Optional<?> getValue() throws ValueResolutionException {
        try {
            method.setAccessible(true);
            return Optional.ofNullable(method.invoke(instance));
        } catch (InvocationTargetException x) {
            throw new ValueResolutionException(
                    String.format("Error invoking method %s on instance %s", method.getName(), instance.toString()), x);
        } catch (IllegalAccessException x) {
            throw new ValueResolutionException(
                    String.format("Error invoking method %s on instance %s", method.getName(), instance.toString()), x);
        }
    }

}

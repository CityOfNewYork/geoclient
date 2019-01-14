package gov.nyc.doitt.gis.geoclient.gradle.ctx;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import gov.nyc.doitt.gis.geoclient.gradle.configuration.Configuration;
import gov.nyc.doitt.gis.geoclient.gradle.configuration.Source;
import gov.nyc.doitt.gis.geoclient.gradle.configuration.ValueSource;

public class Context {

    private static final Logger logger = Logging.getLogger(Context.class);

    private final Project project;
    private final List<Resolution> resolutions;

    public Context(Project project) {
        super();
        this.project = project;
        this.resolutions = new ArrayList<>();
    }

    public void populateValues(Object extension) {
        Map<Method, List<ValueSource>> valueSources = getValueSources(extension);
        for (Map.Entry<Method, List<ValueSource>> entry : valueSources.entrySet()) {
            List<Configuration> configurations = extractConfigurations(extension, entry.getKey(), entry.getValue());
            configurations.forEach(this::resolve);
        }
    }

    protected Map<Method, List<ValueSource>> getValueSources(Object instance) {
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

    protected List<Configuration> extractConfigurations(Object instance, Method method,
            List<ValueSource> valueSources) {
        Objects.requireNonNull(instance, "instance argument cannot be null");
        Objects.requireNonNull(method, "method argument cannot be null");
        Objects.requireNonNull(valueSources, "ValueSources argument cannot be null");
        List<Configuration> result = new ArrayList<>(valueSources.size());
        for (ValueSource vs : valueSources) {
            String key = vs.key();
            Source source = vs.source();
            switch (source) {
            case SYSTEM_PROPERTY:
                result.add(new SystemProperty(key));
                break;
            case PROJECT_PROPERTY:
                result.add(new ProjectProperty(key, project));
                break;
            case ENVIRONMENT_VARIABLE:
                result.add(new EnvironmentVariable(key));
                break;
            case EXTENSION_PROPERTY:
                result.add(new ExtensionProperty(instance, method));
                break;

            default:
                break;
            }
        }
        return result;
    }

    protected boolean resolve(Configuration configuration) {
        logger.info("Resolving {}", configuration);
        Resolution resolution = new Resolution(configuration).resolve();
        logger.info("Result: {}", resolution);
        return this.resolutions.add(resolution);
    }

    public List<Resolution> getResolutions() {
        return Collections.unmodifiableList(this.resolutions);
    }
}

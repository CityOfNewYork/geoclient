package gov.nyc.doitt.gis.geoclient.gradle;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
public class BuildConfigurationResolver {
    final Logger logger = Logging.getLogger(BuildConfigurationResolver.class);
    private final Project project;
    public BuildConfigurationResolver(Project project) {
        this.project = project;
    }
    public String getSystemProperty(String name) {
        String result = System.getProperty(name);
        if(result != null) {
            logger.info("Configuration '{}={}' resolved from Java system property", name, result);
            return result;
        }
        logger.info("Java system property'{}' could not be resolved", name);
        return null;
    }
    public String getEnv(String name) {
        String result = System.getenv(name);
        if(result != null) {
            logger.info("Configuration '{}={}' resolved from environment variable", name, result);
            return result;
        }
        logger.info("Environment variable '{}' could not be resolved", name);
        return null;
    }
    public Object getGradleProperty(String propName) {
        if(this.project.hasProperty(propName)) {
            Object result = this.project.property(propName);
            logger.info("Configuration '{}={}' resolved from Gradle project property", propName, result);
            return result;
        }
        logger.info("Gradle project property '{}' could not be resolved", propName);
        return null;
    }
    public String resolve(String propName, String sysPropName, String defaultValue) {
        Object obj = getGradleProperty(propName);
        if(obj != null) {
            return String.format("%s", obj);
        }
        if(sysPropName != null) {
            String propValue = getSystemProperty(sysPropName);
            if(propValue != null) {
                this.project.setProperty(propName, propValue);
                return propValue;
            }
        }
        if(defaultValue != null) {
            this.project.setProperty(propName, defaultValue);
            logger.info("Configuration property '{}={}' resolved from default value", propName, defaultValue);
            return defaultValue;
        }
        logger.info("Configuration property '{}' could not be resolved", propName);
        return null;
    }
    public String resolveFromEnv(String propName, String envVarName, String defaultValue) {
        String result = getEnv(envVarName);
        if(result != null) {
            this.project.setProperty(propName, result);
            return result;
        }
        if(defaultValue != null) {
            this.project.setProperty(propName, defaultValue);
            logger.info("Configuration property '{}={}' resolved from default value", propName, defaultValue);
            return defaultValue;
        }
        result = "";
        this.project.setProperty(propName, result);
        return result;
    }
}
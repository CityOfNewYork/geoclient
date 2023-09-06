package gov.nyc.doitt.gis.geoclient.gradle

import org.gradle.api.Project
import org.gradle.api.provider.Property

import java.io.File
import java.util.Map

abstract class AbstractExtension {

    protected final Project project

    AbstractExtension(Project project) {
        this.project = project
    }

    abstract Map<String, Object> getEnvironment()
    abstract Map<String, Object> getSystemProperties()

    void resolveConvention(String gradleName, String systemName, String envName, Property<String> property, String defaultValue) {
        //println "[AbstractExtension] resolveConvention(gradleName:${gradleName}, systemName:${systemName}, envName:${envName}, property:${property}, defaultValue:${defaultValue})"
        Object value = gradleProperty(gradleName)
        if(value) {
            //println "[AbstractExtension] Found Gradle property ${gradleName}: ${value}"
            property.convention(value.toString())
            return
        }
        String stringValue = systemProperty(systemName)
        if(stringValue) {
            //println "[AbstractExtension] Found System property ${systemName}: ${stringValue}"
            property.convention(stringValue)
            project.ext.gradleName = stringValue
            return
        }
        stringValue = environmentVariable(envName)
        if(stringValue) {
            //println "[AbstractExtension] Found environment variable ${envName}: ${stringValue}"
            property.convention(stringValue)
            project.ext.gradleName = stringValue
            return
        }
        property.convention(defaultValue)
        project.ext.gradleName = defaultValue
        //println "[AbstractExtension] Using default value for project.ext.${gradleName}: ${defaultValue}"
    }

    Object gradleProperty(String name) {
        project.findProperty(name)
    }

    String systemProperty(String name) {
        System.getProperty(name)
    }

    String environmentVariable(String name) {
        System.getenv(name)
    }
}

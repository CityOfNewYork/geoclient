package gov.nyc.doitt.gis.geoclient.gradle.plugin

import org.gradle.api.Project

class Property {
    
    PropertySource gradle
    PropertySource system
    PropertySource env

    String name() {
        gradle.name
    }
    
    Object value() {
        gradle.value
    }
    
    // Priority order: gradle prop, system prop, env var.
    // First found is used to set the gradle property (if it's not the gradle property itself).
    // However, the other sources are checked and populated if available.
    Property resolve(Project project, String gradleName, String systemName, String envName, Object defaultValue) {
        
        boolean gradleResolved = this.gradle.isPresent
        
        if(!gradleResolved) {
            resolveGradleProperty(project, gradleName)
        }
        
        boolean systemResolved = resolveSystemProperty(systemName)
        boolean envResolved = resolveEnvironmentVariable(envName)
        
        if(!gradleResolved) {
            if(systemResolved) {
                this.gradle.value = this.system.value
            } else if(envResolved) {
                this.gradle.value = this.env.value
            } else {
                this.gradle.value = defaultValue
                this.gradle.source = Source.DEFAULT_VALUE
            }
        }
        this
    }
    
    PropertySource setGradleProperty(String name, Object value) {
        this.gradle = new PropertySource(name: name, value: value, source: Source.GRADLE_PROPERTY)
    }
    
    private boolean resolveGradleProperty(Project project, String name) {
        if(project == null) {
            throw new NullPointerException("Project argument cannot be null")
        }
        if(name == null) {
            throw new NullPointerException("gradle property name argument cannot be null")
        }
        Object value = project.findProperty(name)
        if(value) {
            this.gradle = new PropertySource(key: name, value: value, source: Source.GRADLE_PROPERTY)
            true
        }
        false
    }
    
    private boolean resolveSystemProperty(String name) {
        if(name != null) {
            Object value = System.getProperty(name)
            if(value) {
                this.system = new PropertySource(key: name, value: value, source: Source.SYSTEM_PROPERTY)
                true
            }
        }
        false
    }

    private boolean resolveEnvironmentVariable(String name) {
        if(name != null) {
            Object value = System.getenv(name)
            if(value) {
                this.system = new PropertySource(key: name, value: value, source: Source.ENVIRONMENT_VARIABLE)
                true
            }
        }
        false
    }

}
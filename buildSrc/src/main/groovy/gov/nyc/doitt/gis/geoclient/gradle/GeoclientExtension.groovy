package gov.nyc.doitt.gis.geoclient.gradle

import org.gradle.api.Project
import org.gradle.api.provider.Property

import java.util.Map

class GeoclientExtension extends AbstractExtension {

    static final String GC_JNI_VERSION_GRADLE = 'gcJniVersion'
    static final String GC_JNI_VERSION_SYSTEM = 'gc.jni.version'
    static final String GC_JNI_VERSION_ENVVAR = 'GC_JNI_VERSION'

    // Default is dynamic so there is no constant 'DEFAULT_GC_JNI_VERSION'

    private final Property<String> jniVersion

    GeoclientExtension(Project project) {
        super(project)
        this.jniVersion = project.getObjects().property(String)
        resolveConventions()
    }

    void resolveConventions() {
        resolveConvention(GC_JNI_VERSION_GRADLE, GC_JNI_VERSION_SYSTEM, GC_JNI_VERSION_ENVVAR, jniVersion, project.version)
    }

    Property<String> getJniVersion() {
        this.jniVersion
    }

    void setJniVersion(String value) {
        this.jniVersion.set(value)
    }

    Map<String, Object> getEnvironment() {
        def result = [:] as Map<String, Object>
        result."${GC_JNI_VERSION_ENVVAR}" = this.jniVersion.getOrNull()
        result
    }

    Map<String, Object> getSystemProperties() {
        def result = [:] as Map<String, Object>
        result."${GC_JNI_VERSION_SYSTEM}" = this.jniVersion.getOrNull()
        result
    }

    @Override
    String toString() {
        this.getClass().getSimpleName() + "[ jniVersion=${jniVersion.getOrNull()} ]"
    }
}

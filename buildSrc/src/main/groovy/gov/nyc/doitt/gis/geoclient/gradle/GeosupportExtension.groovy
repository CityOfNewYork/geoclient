package gov.nyc.doitt.gis.geoclient.gradle

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.Project
import org.gradle.api.provider.Property

import java.util.Map

class GeosupportExtension extends AbstractExtension {

    static final String GS_HOME_GRADLE = 'gsHome'
    static final String GS_HOME_SYSTEM = 'gs.home'
    static final String GS_HOME_ENVVAR = 'GEOSUPPORT_HOME'
    static final String GS_GEOFILES_GRADLE = 'gsGeofiles'
    static final String GS_GEOFILES_SYSTEM = 'gs.geofiles'
    static final String GS_GEOFILES_ENVVAR = 'GEOFILES'
    static final String GS_INCLUDE_PATH_GRADLE = 'gsIncludePath'
    static final String GS_INCLUDE_PATH_SYSTEM = 'gs.include.path'
    static final String GS_INCLUDE_PATH_ENVVAR = 'GS_INCLUDE_PATH'
    static final String GS_LIBRARY_PATH_GRADLE = 'gsLibraryPath'
    static final String GS_LIBRARY_PATH_SYSTEM = 'gs.library.path'
    static final String GS_LIBRARY_PATH_ENVVAR = 'GS_LIBRARY_PATH'

    static final boolean isWindows = Os.isFamily(Os.FAMILY_WINDOWS)

    static final String DEFAULT_GS_LINUX_HOME = "/opt/geosupport";
    static final String DEFAULT_GS_WINDOWS_HOME = "c:/lib/geosupport/current";
    static final String DEFAULT_GS_HOME = isWindows ? DEFAULT_GS_WINDOWS_HOME : DEFAULT_GS_LINUX_HOME;
    static final String DEFAULT_GS_GEOFILES = DEFAULT_GS_HOME + "/fls/"; // Trailing slash required!
    static final String DEFAULT_GS_LIBRARY_PATH = DEFAULT_GS_HOME + "/lib";
    // Default is dynamic so there is no constant 'DEFAULT_GS_INCLUDE_PATH'

    private final Property<String> home
    private final Property<String> geofiles
    private final Property<String> libraryPath
    private final DirectoryProperty includePath

    @javax.inject.Inject
    GeosupportExtension(Project project) {
        super(project)
        this.home = project.objects.property(String)
        this.geofiles = project.objects.property(String)
        this.libraryPath = project.objects.property(String)
        this.includePath = project.objects.directoryProperty()
    }

    void resolveConventions() {
        resolveConvention(GS_HOME_GRADLE, GS_HOME_SYSTEM, GS_HOME_ENVVAR, this.home, DEFAULT_GS_HOME)
        resolveConvention(GS_GEOFILES_GRADLE, GS_GEOFILES_SYSTEM, GS_GEOFILES_ENVVAR, this.geofiles, DEFAULT_GS_GEOFILES)
        resolveConvention(GS_LIBRARY_PATH_GRADLE, GS_LIBRARY_PATH_SYSTEM, GS_LIBRARY_PATH_ENVVAR, this.libraryPath, DEFAULT_GS_LIBRARY_PATH)
        Directory includeDir = project.project(':geoclient-jni').dir('lib/geosupport/headers')
        this.includePath.convention(includeDir)
        project.property(GS_INCLUDE_PATH, includeDir.getAsFile().toString())
    }

    Property<String> getHome() {
        this.home
    }

    void setHome(String value) {
        this.home.set(value)
    }

    Property<String> getGeofiles() {
        this.geofiles
    }

    void setGeofiles(String value) {
        this.geofiles.set(value)
    }

    Property<String> getLibraryPath() {
        this.libraryPath
    }

    void setLibraryPath(String value) {
        this.libraryPath.set(value)
    }

    DirectoryProperty getIncludePath() {
        this.includePath
    }

    void setIncludePath(File value) {
        this.includePath.set(value)
    }

    String getIncludeDirAsString() {
        DirectoryProperty includeDir = this.includePath.getOrNull()
        String string = ""
        if(includeDir) {
            string = includeDir.getAsFile().toString()
        }
        string
    }

    Map<String, Object> environment() {
        [GS_HOME_ENVVAR: this.home.getOrNull(),
         GS_GEOFILES_ENVVAR: this.geofiles.getOrNull(),
         GS_LIBRARY_PATH_ENVVAR: this.libraryPath.getOrNull(),
         GS_INCLUDE_PATH_ENVVAR: getIncludeDirAsString()]
    }

    Map<String, Object> systemProperties() {
        [GS_HOME_SYSTEM: this.home.getOrNull(),
         GS_GEOFILES_SYSTEM: this.geofiles.getOrNull(),
         GS_LIBRARY_PATH_SYSTEM: this.libraryPath.getOrNull(),
         GS_INCLUDE_PATH_SYSTEM: getIncludeDirAsString()]
    }

}

package gov.nyc.doitt.gis.geoclient.gradle.plugin

import java.nio.file.Paths
import java.nio.file.Files
import java.util.Map
import org.apache.tools.ant.taskdefs.condition.Os

class Geosupport {
    
    static final String GS_HOME_GRADLE = 'gsHome'
    static final String GS_HOME_SYSTEM = 'gs.home'
    static final String GS_HOME_ENVVAR = 'GEOSUPPORT_HOME'
    static final String GS_GEOFILES_GRADLE = 'gsGeofiles'
    static final String GS_GEOFILES_SYSTEM = 'gs.geofiles'
    static final String GS_GEOFILES_ENVVAR = 'GEOFILES'
    static final String GS_LIBRARY_PATH_GRADLE = 'gsLibraryPath'
    static final String GS_LIBRARY_PATH_SYSTEM = 'gs.library.path'
    static final String GS_LIBRARY_PATH_ENVVAR = 'GS_LIBRARY_PATH'
    
    boolean isWindows = Os.isFamily(Os.FAMILY_WINDOWS)
    
    Property home
    Property geofiles
    Property libraryPath
}
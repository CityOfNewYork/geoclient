package com.digitalclash.geoclient.gradle.internal

//import java.nio.file.Files
//import java.nio.file.Path

import spock.lang.Specification
//import spock.lang.TempDir
import spock.lang.Unroll
import spock.util.environment.RestoreSystemProperties

class GeosupportConfigResolverTest extends Specification {
    //@TempDir
    //Path temporaryFolder

    @RestoreSystemProperties
    @Unroll
    def "returns default configuration for #osName"() {
        given:
        System.setProperty('os.name', osName)
        // Use Spy to override base class #getEnv method so actual environment variables
        // don't interfere.
        GeosupportConfigResolver configResolver = Spy(GeosupportConfigResolver.class)
        configResolver.getEnv(_) >> null

        expect:
        configResolver.getHome() == geosupportHome
        configResolver.getIncludePath() == new File(geosupportHome, '/include')
        configResolver.getLibraryPath() == "${geosupportHome}/lib"
        configResolver.getGeofiles() == "${geosupportHome}/fls/"

        where:
        osName       | geosupportHome
        'Linux'      | '/opt/geosupport/current'
        'Windows 10' | 'c:/opt/geosupport/current'
    }
}

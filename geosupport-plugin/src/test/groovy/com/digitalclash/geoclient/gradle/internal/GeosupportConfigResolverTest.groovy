package com.digitalclash.geoclient.gradle.internal

//import java.nio.file.Files
//import java.nio.file.Path

import spock.lang.Specification
//import spock.lang.TempDir
import spock.lang.Unroll
import spock.util.environment.RestoreSystemProperties

import static GeosupportConfigResolver.*

class GeosupportConfigResolverTest extends Specification {
    //@TempDir
    //Path temporaryFolder

    @RestoreSystemProperties
    @Unroll
    def "returns default value for #osName when no Geosupport configuration is set"() {
        given:
        System.setProperty('os.name', osName)
        // Use Spy to override base class #getEnv method so actual environment variables
        // don't interfere.
        GeosupportConfigResolver configResolver = Spy(GeosupportConfigResolver.class)
        // No Geosupport system properties or environment variables are set
        configResolver.getEnv(_) >> null
        System.getProperty(GS_HOME_SYSTEM) >> null
        System.getProperty(GS_INCLUDE_PATH_SYSTEM) >> null
        System.getProperty(GS_LIBRARY_PATH_SYSTEM) >> null
        System.getProperty(GS_GEOFILES_SYSTEM) >> null

        expect:
        configResolver.getHome() == geosupportHome
        configResolver.getIncludePath() == "${geosupportHome}/include"
        configResolver.getLibraryPath() == "${geosupportHome}/lib"
        configResolver.getGeofiles() == "${geosupportHome}/fls/"

        where:
        osName       | geosupportHome
        'Linux'      | '/opt/geosupport/current'
        'Windows 10' | 'c:/opt/geosupport/current'
    }

    @RestoreSystemProperties
    def "uses system property over environment variable"() {
        given:
        GeosupportConfigResolver configResolver = Spy(GeosupportConfigResolver.class)
        System.setProperty('os.name', 'Linux')
        System.setProperty(GS_HOME_SYSTEM, '/opt/goat')
        System.setProperty(GS_INCLUDE_PATH_SYSTEM, '/opt/include')
        System.setProperty(GS_LIBRARY_PATH_SYSTEM, '/opt/lib')
        System.setProperty(GS_GEOFILES_SYSTEM, '/opt/fls/')
        configResolver.getEnv(GS_HOME_ENVVAR) >> '/usr/local/goat'
        configResolver.getEnv(GS_INCLUDE_PATH_ENVVAR) >> '/usr/local/include'
        configResolver.getEnv(GS_LIBRARY_PATH_ENVVAR) >> '/usr/local/lib'
        configResolver.getEnv(GS_GEOFILES_ENVVAR) >> '/usr/local/fls/'

        expect:
        configResolver.getHome() == '/opt/goat'
        configResolver.getIncludePath() == '/opt/include'
        configResolver.getLibraryPath() == '/opt/lib'
        configResolver.getGeofiles() == '/opt/fls/'
    }

    @RestoreSystemProperties
    def "uses environment variable when system property not set"() {
        given:
        System.setProperty('os.name', 'Linux')
        GeosupportConfigResolver configResolver = Spy(GeosupportConfigResolver.class)
        configResolver.getSystemProperty(GS_HOME_SYSTEM) >> null
        configResolver.getSystemProperty(GS_INCLUDE_PATH_SYSTEM) >> null
        configResolver.getSystemProperty(GS_LIBRARY_PATH_SYSTEM) >> null
        configResolver.getSystemProperty(GS_GEOFILES_SYSTEM) >> null
        configResolver.getEnv(GS_HOME_ENVVAR) >> '/usr/local/goat'
        configResolver.getEnv(GS_GEOFILES_ENVVAR) >> '/usr/local/fls/'
        configResolver.getEnv(GS_LIBRARY_PATH_ENVVAR) >> '/usr/local/lib'
        configResolver.getEnv(GS_INCLUDE_PATH_ENVVAR) >> '/usr/local/include'

        expect:
        configResolver.getHome() == '/usr/local/goat'
        configResolver.getIncludePath() == '/usr/local/include'
        configResolver.getLibraryPath() == '/usr/local/lib'
        configResolver.getGeofiles() == '/usr/local/fls/'
    }
}

package gov.nyc.doitt.gis.geoclient.gradle.plugin

final class JavaRuntime {
    private static final String gradleHome      = System.getenv('GRADLE_HOME')
    private static final String javaHome        = System.getenv('JAVA_HOME')
    private static final String javaIoTmpDir    = System.getProperty('java.io.tmpdir')
    private static final String javaLibraryPath = System.getProperty('java.library.path')
    private static final String osArch          = System.getProperty('os.arch')
    private static final String osName          = System.getProperty('os.name')
    private static final String userDir         = System.getProperty('user.dir')
    private static final String userHome        = System.getProperty('user.home')

    String getGradleHome() {
        gradleHome
    }

    String getJavaHome() {
        javaHome
    }

    String getJavaIoTmpDir() {
        javaIoTmpDir
    }

    String getJavaLibraryPath() {
        javaLibraryPath
    }

    String getOsArch() {
        osArch
    }

    String getOsName() {
        osName
    }

    String getUserDir() {
        userDir
    }

    String getUserHome() {
        userHome
    }
}

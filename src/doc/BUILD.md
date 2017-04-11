# Building Geoclient

Geoclient currently uses [Gradle](https://docs.gradle.org/current/release-notes) to build both the Java and C code necessary for calling Geosupport. Gradle is an awesome build tool, but its original design was geared towards building Java artifacts and its support for native (C/C++ in this case) is still changing rapidly.

There are also a few platform-specific quirks when compiling and linking against the Geosupport C API. A similar caveat applies to the cartesian product of possible operating systems, Java versions, C toolchains and so on.

There is nothing special about Geoclient's Java or C sources and once the JNI header files have been generated (currently by Gluegen) the sources for each language can be built independently using the tools of your choice. However, the documentation in this section assumes you are building Geoclient from source using the provided Gradle build files.

With this in mind, here's a laundry list of stacks that have worked for purposes of development, automated unit and functional testing using the current Gradle build configuration:

| Operating System | Distribution                          | JDK (64-bit only)               | Gradle | C Compiler                                                                               |
|----------------- | ------------------------------------- | ------------------------------- | ------ | ---------------------------------------------------------------------------------------- |
| GNU/Linux x86_64 | RHEL 6.3+/7, CentOS 6.7+/7, Ubuntu 14 | Oracle 1.7/1.8, OpenJDK 1.7/1.8 | 2.14   | gcc/g++ 4.7+                                                                             |
| Windows x64      | 7/10                                  | Oracle 1.7/1.8                  | 2.14   | gcc/g++ 4.7+ (mingw-w64-x86_64-gcc-4.7+ toolchain from [MSYS2](http://msys2.github.io/)) |

**For high-performance, production environments**, the majority of our direct experience is running Geosupport and Geoclient on RHEL 6+ and Oracle JDK 1.7+. Other Linux/Java platform combinations should perform similarly, but they have not been "battle-tested" by the Geoclient developers.

At this time, only the 64-bit version of Geosupport is supported, meaning that the 64-bit versions of both the Java and C compilers are required at build time (Normally, pure Java applications can be built with either the 32-bit or 64-bit `javac` compiler since it generates platform/architecture neutral bytecode. Since Geoclient uses JNI to call into the C runtime, the proper 'bit-ness' must also be used at compile time).

## Concepts

### Geoclient's compile, link and runtime requirements

Geoclient relies on [the Department of City Planning's Geosupport geocoder](http://www1.nyc.gov/site/planning/data-maps/open-data.page#geocoding_application) to do anything useful. On Linux, Windows, and (soon, hopefully) OSX, Geosupport is distributed as a handful of C shared libraries and proprietary data files.

Geoclient is written in Java and currently uses the JDK's mysterious [JNI](https://en.wikipedia.org/wiki/Java_Native_Interface) API to make "function calls" into Geosupport from a running JVM. At a high level, building Geoclient from source on any supported platform, requires that everyone in both C and Java-land know what's going on.

**To build Geoclient from source**, it's necessary that:
>- the C compiler can find the header files for Geosupport, Geoclient, the JDK (Java), and the platform's standard C libs.
>- the linker can find the C libraries for Geosupport, Geoclient, the JDK and the standard C libs.
>- the Java compiler can find any Java components not defined by the Geoclient Java source itself (e.g. the CLASSPATH for external jar files).

**At runtime, Geosupport needs to know** where to find:
>- its own data files (GEOFILES)
>- its own C libraries (see above).

**At runtime, the JVM must know** where to find:
>- Geoclient's classes and external class dependencies (see above)
>- it's own built-in classes (JRE 1.7 or 1.8)
>- the Geosupport and platform C libraries (see above).

### Compiling the Geoclient C files

Geoclient contains a thin layer ("waaferr thin") of C that exists primarily to abstract the platform-specific naming conventions and differing runtime requirements Geosupport uses on each platform. Geoclient uses [Gradle](https://gradle.org) build, test and package both the C and Java code base.

While this will be increasingly awesome (hopefully) as Gradle evolves, it currently is a big, fat P.I.A. because, on Windows, linking against the latest versions of Geosupport requires Visual Studio 2015. At the moment, Gradle doesn't support VS 2015 (there is a [long-awaited patch stuck in the release queue](https://github.com/gradle/gradle/pull/500)) and it's likely that some TechNet research or ugly hard-coded path hack will work but we're using `mingw-w64-x86_64-gcc` from [MSYS2](http://msys2.github.io/) for now.

**The location of the Geosupport shared libraries.**
Either of the following should work:
>  1. Set environment variable `GS_LIBRARY_PATH=<geosupport install>/lib`
>  2. Specify the `gsLibraryPath` property in the `gradle.properties` file in the base Geoclient source tree or on the command line as a Gradle project property `-PgsLibraryPath=<geosupport install>/lib`

**The location of Geosupport's required GEOFILES environment variable**
>  1. Set environment variable `GEOFILES=<geosupport install>/fls`
>  2. Specify the `gsGeofiles` property in the `gradle.properties` file in the base Geoclient source tree or on the command line as a Gradle project property `-PgsGeofiles=<geosupport install>/fls`

Note that Geosupport requires this environment variable be set at runtime and that on Windows it must end with a trailing file separator (`GS_LIBRARY_PATH=<geosupport install>/Fls/`). The Gradle build will export this environment variable automatically if it is not set _and_ `gsGeofiles` _is specified_ but this will only be visible to the forked JVM used when Gradle runs test tasks.)

**The location of the correct Geosupport header files**
>  1. Set environment variable `GS_INCLUDE_PATH=<geosupport install>/include/foruser`
>  2. Specify the `gsIncludePath` property in the `gradle.properties` file in the base Geoclient source tree or on the command line as a Gradle project property `-PgsIncludePath=<geosupport install>/include/foruser`

Note that the location of Geosupport include directory may just be `<geosupport_install>/include` on some platforms. ( **ProTip: look for the `*.h` files** :o)

*TODO*

* Table of config source, owner, platform, etc.

### Installing Geosupport

*TODO*

* Versioning: releases vs. versions
* Downloading
* Install, copy, remove
* 64-bit version
* Header file fixes
* Library and include path setup
* Verification

### Java

*TODO*

* java.library.path
* Hackety-hacks: LD_LIBRARY_PATH for Linux, PATH for Windows
* Use of Gluegen to generate optimized JNI code
* Deployment recipes: Tomcat Servlet context, CLI, etc.

### Gradle - v2.14+ (v3 support coming soon)

*TODO*

* Environment variables: JAVA_HOME, GRADLE_USER_HOME, etc...
* Gradle API changes after 2.9

### Linux

**OpenJDK**
Many Linux distros come pre-configured with OpenJDK's open-source Java SE implementation. In some cases, only the JRE is installed by default so you will need to install the JDK yourself before building Geoclient.

| Linux Distro         |  Package                      | Type    | Build works? |
| -------------------- |  ---------------------------- | ------- | ------------ |
| Debian, Ubuntu, etc. |  openjdk-8-jre                | JRE     | No           |
|                      |  **openjdk-8-jdk**            | **JDK** | **Yes**      |
| Fedora, RHEL, etc.   |  java-1.8.0-openjdk           | JRE     | No           |
|                      |  **java-1.8.0-openjdk-devel** | **JDK** | **Yes**      |

The naming conventions can be confusing, so be sure to check the [OpenJDK](http://openjdk.java.net/install/) site for information on installing the relevant platform-specific JDK package.

We haven't run Geoclient on OpenJDK in a high-volume, production environment and don't know whether this combination is ready for prime-time yet.

*TODO*
* Moar!

### Windows

#### Install MSYS2 and MinGW-w64 gcc Compiler Toolchain

The following instructions are a less detailed summary based on this Stackoverflow [post](http://stackoverflow.com/questions/30069830/how-to-install-mingw-w64-and-msys2):

1. Install the latest stable __64-bit__ version of the MSYS2 shell as described on the [MSYS2 homepage](http://msys2.github.io/). Follow the directions closely including post install configuration (the last step (step 7) showing an example of how to install other packages can safely be skipped if you don't want to install Git)

2. If the MSYS2 shell is not still open, run it again by selecting `Start->All Programs->MSYS2 64bit->MinGW-w64 Win64 Shell` or, assuming you accepted the install directory defaults, just double-clicking `C:\msys64\mingw64_shell.bat`

3. At the shell prompt, use `pacman` (the MSYS2 package manager) to install the gcc 64-bit toolchain:

    pacman -S mingw-w64-x86_64-gcc

4. Verify that gcc is working and g++ is included on the path:

    $ gcc -v
    ... (lots of info)

    $ g++ -v
    ... (same info)

Again, the Stackoverflow [post](http://stackoverflow.com/questions/30069830/how-to-install-mingw-w64-and-msys2) mentioned above provides more descriptive instructions.

*TODO*
* MSYS2 binary requires PATH
* Oracle JDK 1.8: Building with `mingw-w64-x86_64-gcc` may require that the `<JAVA_HOME>/include/win32/jni_md.h` file be tweaked with a macro to deal with the `__int64` type. See `geoclient-native/etc/jni_md.h` for an example workaround.

### OSX

Currently, the Department of City Planning does not provide an OS X compatible Geosupport application binary. When/if DCP releases binaries, Geoclient will be able to provide this feature.

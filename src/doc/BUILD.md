# Building Geoclient

While it is possible to use other compilers, toolchains, and tools, Geoclient currently uses [Gradle](https://docs.gradle.org/current/release-notes) to build both the Java and C code needed to allow Geoclient to call Geosupport. Gradle is an awesome build tool, but its original design was geared towards building Java artifacts and its support for native (C/C++ in this case) is still changing rapidly. As such, these instructions mention specific versions of compilers, toolchains, etc. that have been tested successfully on each platform. 

The following recipes have worked:

| Operating System | Distribution                   | JDK (64-bit only)               | C Compiler                                                                        | Gradle |
|------------------|--------------------------------|---------------------------------|-----------------------------------------------------------------------------------|--------|
| GNU/Linux x86_64 | RHEL 6.3+, 7+, CentOS 6.7+,7+  | OpenJDK 1.7.x, Oracle JDK 1.7.x | gcc, g++ 4.7+ (g++ is requried)                                                   | 2.11+  |
| Windows - 64-bit | 7, 10                          | Oracle JDK 1.7.x                | gcc 4.7+ (g++ is included), msys2 with mingw-w64-x86_64-gcc-4.7.+ toolchain  | 2.11+  |
| OS X             | N/A                            | N/A                             | N/A                                                                               | N/A    |

However, since there is nothing unique about the Geoclient Java or C sources and each can be built separately using the tools or your choice assuming you are knowledgeable about the compilers, platforms, and runtimes.

At this time, only the 64-bit version of Geosupport is supported, meaning that the 64-bit versions of both the Java and C compilers are required at build time (Normally, pure Java applications can be built with either the 32-bit or 64-bit `javac` compiler since it generates platform/architecture neutral bytecode. Since Geoclient uses JNI to call into the C runtime, the proper 'bit-ness' must also be used at compile time).

### Prerequisites

#### Java - Oracle 64-bit 1.7 JDK
  * 64-bit JDK/JRE for build/runtime
  * Build requires "full" JDK
  * v1.7.x is currently supported
  * v1.8.x works but building may require path tweaks so the compiler can find JNI headers
  * Oracle JDK is production-tested
  * OpenJDK works but has not been used on production systems. The OpenJDK install may not include the "full" JDK: make sure `openjdk-7-jdk` is installed and not just `openjdk-7-jre`. See [here](http://openjdk.java.net/install/) for more details.

#### Gradle - v2.11+
  * Environment variables: JAVA_HOME, GRADLE_USER_HOME, etc...
  * Gradle API changes after 2.9
   
*TODO*

#### Geosupport - version 15.3+
  * Downloading
  * Install, copy, remove
  * 64-bit version
  * Header file fixes
  * Library and include path setup
  * Verification
  
*TODO*

## Linux

*TODO*

## Windows


### Install MSYS2 and MinGW-w64 gcc Compiler Toolchain

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

## OSX

Currently, the Department of City Planning does not provide an OS X compatible Geosupport application binary. When/if DCP releases binaries, Geoclient will be able to provide this feature.
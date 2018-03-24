# C and JNI Environment Configuration

### Diagnostic Tools

*Show build files which use native compilation variables:*
```bash
find . -name '*.gradle' -type f -exec egrep -li --color '(g[cs][a-z]+Path|geofiles)' {} \;
```
*Show line content of Gradle files using native compilation variables:*
```bash
find . -name '*.gradle' -type f -exec egrep -Hin --color '(g[cs][a-z]+Path|geofiles)' {} \;
```
#### Geoclient Variables
| Gradle Property | Environment Variable | Linux           | Windows              |
|-----------------|----------------------|-----------------|----------------------|
| `gsIncludePath` | `GS_INCLUDE_PATH` | `geo.h`, `pac.h`, `wa_fields.h` | `NYCgeo.h`, `geo.h`, `pac.h`|
| `gsLibraryPath` | `GS_LIBRARY_PATH` |`libNYCgeo.so`, `libStdLast.so`, `libStdUniv.so`, `libapequiv.so`, `libedequiv.so`, `libgeo.so`, `libsan.so`, `libsnd.so`, `libstEnder.so`, `libstExcpt.so`, `libstretch.so`, `libthined.so`|`NYCgeo.dll`, `NYCgeo.lib`, `apequiv.dll`, `edequiv.dll`, `geo.dll`, `san.dll`, `snd.dll`, `stdlast.dll`, `stduniv.dll`, `stender.dll`, `stexcpt.dll`, `stretch.dll`, `thined.dll`|
| `gcIncludePath` | `GC_INCLUDE_PATH` | `geoclient.h`   | `geoclient.h`|
| `gcLibraryPath` | `GC_LIBRARY_PATH` | `libgeoclientjni-linux_x64.so` | `geoclientjni-windows_x64.dll`|
|                       |                 |                      |             |

#### Platform/Runtime Variables
| Java System Property  | Linux Default Value | Windows Default Value | Geoclient Logic |
|-----------------------|---------------------|-----------------------|-----------------|
| `-Djava.library.path` | `LD_LIBRARY_PATH` | `PATH` ||
| `-Djava.home` | `JAVA_HOME` | `JAVA_HOME` | `JNI_JAVA_HOME` |

**Geosupport Include Path**
  * gradle/env.gradle
  * geoclient-generate/build.gradle
  * geoclient-native/build.gradle


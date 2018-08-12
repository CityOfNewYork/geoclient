#ifndef GEOCLIENT_H
#define GEOCLIENT_H

// The Geosupport C API uses platform-dependent function names:
#ifdef _WIN32
  // When compiling with mingw64 on Windows (e.g., MSYS2 MINGW64 Shell)
  // make sure to have gcc tell the linker to add the WIN32 standard call
  // alias: '-Wl,--add-stdcall-alias'. This should result in the preprocessor
  // adding '__stdcall' and a space just before the resolved method name.
  #include "NYCgeo.h"
  #define GEOSUPPORT_FUNC(work_area1, work_area2) NYCgeo(work_area1, work_area2);

  #ifdef DLL_EXPORT
    #define GEOCLIENT_API __declspec(dllexport)
  #else
    // The following allows the geoclient shared lib to compile using VS2015
    //#define GEOSUPPORT_FUNC(work_area1, work_area2) __stdcall NYCgeo(work_area1, work_area2);

    // The following breaks ld when trying to statically link
    // geoclient_test.exe with geoclient.lib on Windows/MSYS2/mingw64:
    //#define GEOCLIENT_API __declspec(dllimport)

    #define GEOCLIENT_API
  #endif
#else
  #include "geo.h"
  #define GEOSUPPORT_FUNC(work_area1, work_area2) geo(work_area1, work_area2);
  #define GEOCLIENT_API
#endif

#ifdef __cplusplus
extern "C" {
#endif

  GEOCLIENT_API void callgeo(char *work_area1, char *work_area2);

#ifdef __cplusplus
}
#endif

#endif /* GEOCLIENT_H */

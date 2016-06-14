#ifndef GEOCLIENT_H
#define GEOCLIENT_H

// The Geosupport C API uses platform-dependent function names:
#ifdef _WIN32
  #include "NYCgeo.h"
  #define GEOSUPPORT_FUNC(work_area1, work_area2) NYCgeo(work_area1, work_area2);
  #ifdef DLL_EXPORT
    # define GEOCLIENT_API __declspec(dllexport)
  #else
    # define GEOCLIENT_API __declspec(dllimport)
  #endif
#else
  #include "geo.h"
  #define GEOSUPPORT_FUNC(work_area1, work_area2) geo(work_area1, work_area2);
  #define GEOCLIENT_API
#endif

// The following ifdef block is the standard way of creating macros which make exporting
// from a DLL simpler. All files within this DLL are compiled with the GEOCLIENT_EXPORT
// symbol defined on the command line by the Gradle build. This symbol should not be
// defined on any project that uses this DLL. This way any other project whose source
// files include this file see GEOCLIENT_API functions as being imported from a DLL,
// whereas this DLL sees symbols defined with this macro as being exported.
// #if defined(_WIN32) && defined(DLL_EXPORT)
// #define GEOCLIENT_API __declspec(dllexport)
// #else
// #define GEOCLIENT_API __declspec(dllimport)
// #endif

#ifdef __cplusplus
extern "C" {
#endif

GEOCLIENT_API void callgeo(char *work_area1, char *work_area2);

#ifdef __cplusplus
}
#endif

#endif /* GEOCLIENT_H */

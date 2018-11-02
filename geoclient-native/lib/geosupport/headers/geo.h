#ifndef NYCgeoH
#define NYCgeoH

/*
 * 2016-01-04 - Matthew Lipper
 *
 * Re-wrote this header to fix broken macro and Linux function definition.
 *
 */

#ifdef __cplusplus
extern "C" {
#endif

#ifdef _WIN32
  void __declspec(dllimport) __stdcall geo(char *ptr_wa1, char *ptr_wa2);
#else
  void geo(char *ptr_wa1, char *ptr_wa2);
#endif

#ifdef __cplusplus
}
#endif

#endif // NYCgeoH

#ifndef NYCgeoH
#define NYCgeoH

#ifdef __cplusplus
extern "C" 
{
#endif

#ifdef __cplusplus
	__declspec(dllimport) void __stdcall NYCgeo(char *ptr_wa1, char *ptr_wa2=null);
#else
	__declspec(dllimport) void __stdcall NYCgeo(char *ptr_wa1, char *ptr_wa2);
#endif

#ifdef __cplusplus
}
#endif

#endif

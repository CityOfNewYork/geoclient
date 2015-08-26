//---------------------------------------------------------------------------
#ifndef NYCgeoH
#define NYCgeoH

#ifdef __cplusplus
extern "C" 
{
	__declspec(dllimport) void __stdcall NYCgeo(char *ptr_wa1, char *ptr_wa2=NULL);
}
#else
{
	__declspec(dllimport) void __stdcall NYCgeo(char *ptr_wa1, char *ptr_wa2);
}
#endif
#endif
//---------------------------------------------------------------------------

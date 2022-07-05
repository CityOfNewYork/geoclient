//---------------------------------------------------------------------------

#ifndef NYCgeoH
#define NYCgeoH
//---------------------------------------------------------------------------

////////////////////////////////////////////////////////////////////////////////
//*** below is the declaration for __stdcall (pascal) 
////////////////////////////////////////////////////////////////////////////////
#ifdef WIN32 
#ifdef __cplusplus                                                      
extern "C" {                                                            
#endif
//void __declspec(dllimport) __stdcall geo(char *ptr_wa1, char *ptr_wa2);
//void ROLE __stdcall geo(char *ptr_wa1, char *ptr_wa2);
void ROLE geo(char *ptr_wa1, char *ptr_wa2);
#ifdef __cplusplus
       }
#endif
//---------------------------------------------------------------------------
#endif
#elif defined (__linux__) 
extern void geo(char *ptr_wa1, char *ptr_wa2);
#endif  
 

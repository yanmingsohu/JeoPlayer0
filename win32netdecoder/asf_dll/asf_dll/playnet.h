// CatfoOD 2009 6 10

#include "asf_dll.h"
#include <wtypes.h>

ASF_DLL_API HRESULT stop();
ASF_DLL_API HRESULT play( BSTR url );
ASF_DLL_API HRESULT pause();
ASF_DLL_API HRESULT resume();
ASF_DLL_API void setvolume(int volume);
ASF_DLL_API DWORD getvolume();
ASF_DLL_API char* getErrStr();
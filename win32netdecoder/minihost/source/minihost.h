// CatfoOD 2009

#include "aeffect.h"
#include "windows.h"

typedef AEffect* (*PluginEntryProc) (audioMasterCallback audioMaster);
VstIntPtr VSTCALLBACK HostCallback (AEffect* effect, VstInt32 opcode, VstInt32 index, VstIntPtr value, void* ptr, float opt);


//-------------------------------------------------------------------------------------------------------
// PluginLoader
//-------------------------------------------------------------------------------------------------------
struct PluginLoader
{
//-------------------------------------------------------------------------------------------------------
	void* module;

	PluginLoader ()
	: module (0)
	{}

	~PluginLoader ()
	{
		if (module)
		{
		#if _WIN32
			FreeLibrary ((HMODULE)module);
		#elif TARGET_API_MAC_CARBON
			CFBundleUnloadExecutable ((CFBundleRef)module);
			CFRelease ((CFBundleRef)module);
		#endif
		}
	}

	bool loadLibrary (char* fileName)
	{
	#if _WIN32
		module = LoadLibraryA (fileName);
	#elif TARGET_API_MAC_CARBON
		CFStringRef fileNameString = CFStringCreateWithCString (NULL, fileName, kCFStringEncodingUTF8);
		if (fileNameString == 0)
			return false;
		CFURLRef url = CFURLCreateWithFileSystemPath (NULL, fileNameString, kCFURLPOSIXPathStyle, false);
		CFRelease (fileNameString);
		if (url == 0)
			return false;
		module = CFBundleCreate (NULL, url);
		CFRelease (url);
		if (module && CFBundleLoadExecutable ((CFBundleRef)module) == false)
			return false;
	#endif
		return module != 0;
	}

	PluginEntryProc getMainEntry ()
	{
		PluginEntryProc mainProc = 0;
	#if _WIN32
		mainProc = (PluginEntryProc)GetProcAddress ((HMODULE)module, "VSTPluginMain");
		if (!mainProc)
			mainProc = (PluginEntryProc)GetProcAddress ((HMODULE)module, "main");
	#elif TARGET_API_MAC_CARBON
		mainProc = (PluginEntryProc)CFBundleGetFunctionPointerForName ((CFBundleRef)module, CFSTR("VSTPluginMain"));
		if (!mainProc)
			mainProc = (PluginEntryProc)CFBundleGetFunctionPointerForName ((CFBundleRef)module, CFSTR("main_macho"));
	#endif
		return mainProc;
	}
//-------------------------------------------------------------------------------------------------------
};
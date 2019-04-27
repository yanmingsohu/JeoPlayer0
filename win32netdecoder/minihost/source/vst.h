// CatfoOD 2009

/*
#include "windows.h"
#include "aeffect.h"
#include "aeffectx.h"
#include "aeffeditor.h"
#include "audioeffect.h"
#include "audioeffectx.h"
*/

#include "minihost.h"
#include "minieditor.h"

#define VSTPLUS extern "C" __declspec(dllexport)


// 效果器最大数量
#define AEC			20
#define MAXV		0xffff
#define abs(X)		((X)>0?(X):-(X))
#define VSTPATH		"VST\\"

const float FHAF	= MAXV/2;

// functions

VSTPLUS void setBufferSize(int s);
VSTPLUS void setSampleRate(float r);
VSTPLUS void openEdit();
VSTPLUS void openAllVst();
VSTPLUS void closeAllVst();
VSTPLUS bool vstIsOpen();
VSTPLUS LPBYTE processing(LPBYTE data, DWORD size);

static void loadVSTConfig();
static void saveVSTConfig();

AEffect* findAEffectOfName(char* name);
char* getAEName(AEffect* ae);
static AEffect* loadvts(char* name,PluginLoader** load);
static void unloadVTS();
static void loadVTSs();
static int rebulidFloat(float*** in, float*** out, float*** swap, DWORD psize);

// struct
/*
* 资料: http://www.tactilemedia.com/info/MCI_Control_Info.html
*/
typedef struct
{
	struct {
		BYTE low;
		char high;
	} lc;				// left ch

	struct {
		BYTE low;
		char high;
	} rc;				// right ch

	// 有符号2字节取样值
	short getL() {
		return ((short)lc.high << 8) | lc.low;
	}
	// 有符号2字节取样值
	short getR() {
		return ((short)rc.high << 8) | rc.low;
	}

	void setL(short d) {
		lc.low = d & 0x0FF;
		lc.high = d >> 8;
	}

	void setR(short d) {
		rc.low = d & 0x0FF;
		rc.high = d >> 8;
	}
} tCh16bit, *PtCh16bit;		// 2声道16bit

typedef struct VST {

	AEffect			*eff;
	char			*name;
	PluginLoader	*loader;

} VST, *PVST;
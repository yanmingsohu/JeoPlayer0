// CatfoOD 2009

#include "stdafx.h"
#include "audioplay.h"
#include "stdlib.h"
#include "playnet.h"

#define MAXVOLUME 1

static char* lasterr = NULL;
static CAudioPlay* core = NULL;
static float currvolume = MAXVOLUME;			// 当前音量值 0.0 - 1.0 （%）
AUDIOSTATUS currstate = CLOSED;

static void exitx();


LPCWSTR covrWT( char* sLinkName ) {
	static WCHAR wstr[MAX_PATH]; 
	MultiByteToWideChar( CP_ACP, 0, sLinkName, strlen(sLinkName), wstr, sizeof(sLinkName) );
	return wstr;
}

bool init() {
	setBufferSize(32767);

	if (core == NULL) {
		core = new CAudioPlay();
		if (core!=NULL) {
			HRESULT r = core->Init();
			return SUCCEEDED(r);
		}
	}
	return FALSE;
}

HRESULT close() {
	return core->Close();
}

ASF_DLL_API HRESULT stop() {
	//if ( currstate == PLAY ) {
		return core->Stop();
		close();
	//}
	//return -1;
}

// play之前需要stop()
ASF_DLL_API HRESULT play( BSTR url ) {
	HRESULT r = core->Open(url);
	if ( SUCCEEDED(r) ) {
		r = core->Start();
		if ( SUCCEEDED(r) ) {
			return r;
		}
	}

	printf("Dll play err : (%x):%s\n", r, lasterr);
	return r;
}

ASF_DLL_API HRESULT pause() {
	if ( currstate == PLAY ) {
		return core->Pause();
	}
	return -1;
}

ASF_DLL_API HRESULT resume() {
	if ( currstate == PAUSE ) {
		return core->Resume();
	}
	return -1;
}

static void exitx() {
//	core->SetVolume(volume);
	close();
	core->Exit();
	core->Release();
	core = NULL;
}

// 输入值根据volume换算为%, 输入范围 0-100, 超出范围会出爆音
ASF_DLL_API void setvolume(int volume) {
//	core->SetVolume(volume);
	currvolume = (double)volume / 100;
//	printf("%f %d\n", currvolume, volume);
}

ASF_DLL_API DWORD getvolume() {
//	return core->getVolume();
	return ( DWORD ) ( currvolume*100 );
}

ASF_DLL_API char* getErrStr() {
	return lasterr;
}

void setErrStr(char* str) {
	lasterr = str;
}


// data	- pcm data
// len	- data length
void changePcmVol(LPBYTE data, DWORD len) {
	if (currvolume >= MAXVOLUME) return;
	tCh16bit* sdata = (tCh16bit*) data;

	for (DWORD i=0; i<len/4; i++) {
		sdata[i].setL( sdata[i].getL() * currvolume );
		sdata[i].setR( sdata[i].getR() * currvolume );
	}
}

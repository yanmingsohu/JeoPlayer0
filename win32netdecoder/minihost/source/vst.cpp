// CatfoOD 2009.5.27

#include "stdio.h"
#include "vst.h"
#include "io.h"
#include "time.h"
#include "SSE.h"


static AEffect* aeffects[AEC];
static char* effname[AEC];
static int aecont = 0;
static bool closevts = TRUE;
static char CONFILE[]  = "config\\vst.conf";
static const char* CONFSTR = "%s %d %f\n";
static PluginLoader* loader[AEC];


extern void setOutrangeVSTname(const char* name);
static AEffect* loadvts(char* name);


BOOL APIENTRY DllMain( HMODULE hModule,
                       DWORD  ul_reason_for_call,
                       LPVOID lpReserved
					 )
{
	static bool inited = FALSE;

	switch (ul_reason_for_call)
	{
	case DLL_PROCESS_ATTACH: 
		break;

	case DLL_THREAD_ATTACH:
		if (!inited) {
			loadVTSs();
			loadVSTConfig();
			inited = TRUE;
		}
		break;

	case DLL_THREAD_DETACH:
		if (inited) 
			saveVSTConfig();
		break;

	case DLL_PROCESS_DETACH: {
		/*
		closeAllVst();
		unloadVTS();
		rebulidFloat(NULL, NULL, NULL, -1);
		*/
		} 
		break;
	}
    return TRUE;
}

static void unloadVTS() {
	for (int i=0; i<aecont; ++i) {
		aeffects[i]->dispatcher (aeffects[i], effClose, 0, 0, 0, 0);
		delete loader[i];
	}
	aecont = 0;
}

//VSTPLUS 
static void loadVTSs() {
	struct _finddata_t ffblk;
	int done;
	intptr_t hand;
	char* file;
	
	hand = _findfirst( VSTPATH"*.dll",&ffblk );
	if (hand!=-1) {

		do {

			file = new char[ strlen(VSTPATH) + strlen(ffblk.name) +1 ];
			file[0] = '\0';
			strcat(file, VSTPATH);
			strcat(file, ffblk.name);
			PluginLoader* load = NULL;
			AEffect* aef = loadvts( file, &load );

			if (aef) {
				aef->dispatcher (aef, effOpen, 0, 0, 0, 0);
				aeffects[aecont] = aef;
				loader[aecont] = load;
				effname[aecont] = new char[ strlen(ffblk.name) +1 ];
				strcpy(effname[aecont], ffblk.name);
				aecont++;
				
				printf("load VST : %s \n", ffblk.name);
			}
			delete [] file;
			done = _findnext(hand, &ffblk);

		} while (!done);

	}
}

static AEffect* loadvts(char* name, PluginLoader** load) {
	PluginLoader* loader = new PluginLoader();

	if (!loader->loadLibrary (name))
	{
		printf ("Failed to load VST Plugin library : %s\n", name);
		return NULL;
	}

	PluginEntryProc mainEntry = loader->getMainEntry ();
	if (!mainEntry)
	{
		printf ("VST Plugin main entry not found : %s\n", name);
		return NULL;
	}

	AEffect* effect = mainEntry (HostCallback);
	if (!effect)
	{
		printf ("Failed to create effect instance : %s\n", name);
		return NULL;
	}
	*load = loader;
	return effect;
}

static void loadVSTConfig() {
	FILE* file = fopen( CONFILE, "rt" );

	if (file) {

		char name[256];
		float volume;
		int paraIx;
		while ( !feof( file ) ) {
			fscanf( file, CONFSTR, name, &paraIx, &volume);
			AEffect* eff = findAEffectOfName( name );
			if (eff) {
				eff->setParameter( eff, paraIx, volume );
			}
		}

	} else {
		printf("can't open vst config file to load.\n");
	}
}

static void saveVSTConfig() {
	FILE* file = fopen( CONFILE, "wt" );

	if (file) {

		for (int i=0; i<aecont; ++i) {

			AEffect* effect = aeffects[i];
			char* name = effname[i];

			for (int pi = 0; pi < effect->numParams; pi++) {
				float value = effect->getParameter (effect, pi);
				fprintf( file, CONFSTR, name, pi, value);
			}
		}

		fclose( file );

	} else {
		printf("can't save vst config file.\n");
	}
}

VSTPLUS void openEdit() {
	checkEffectEditor( aeffects, aecont );
}

VSTPLUS void setBufferSize(int s) {
	printf("vst buffer size: %d\n", s);
	for (int i=0; i<aecont; ++i) {
		aeffects[i]->dispatcher (aeffects[i], effSetBlockSize, 0, s, 0, 0);
	}
}

VSTPLUS void setSampleRate(float r) {
	printf("vst sample rate: %f\n", r);
	for (int i=0; i<aecont; ++i) {
		aeffects[i]->dispatcher (aeffects[i], effSetSampleRate, 0, 0, 0, r);
	}
}

VSTPLUS void openAllVst() {
	for (int i=0; i<aecont; ++i) {
		aeffects[i]->dispatcher (aeffects[i], effMainsChanged, 0, 1, 0, 0);
	}
	closevts = FALSE;
}

VSTPLUS void closeAllVst() {
	for (int i=0; i<aecont; ++i) {
		aeffects[i]->dispatcher (aeffects[i], effMainsChanged, 0, 0, 0, 0);
	}
	closevts = TRUE;
}

VSTPLUS bool vstIsOpen() {
	return !closevts;
}


static int rebulidFloat(float*** in, float*** out, DWORD psize) {
	const static int CH = 2;

	static DWORD currentsize = 0 ;
	static float** fin = NULL;
	static float** fout = NULL;

	if ( currentsize>0 && (psize<0  || (psize>0 && currentsize<psize) ) ) {

		for (int ch=0; ch<CH; ++ch) {	
			delete[] fin[CH];
			delete[] fout[CH];
		}

		delete [] fin;
		delete [] fout;
	}

	if ( psize>0 ) {

		if ( currentsize<psize ) {
			fin = new float*[CH];
			fout= new float*[CH];
			
			int factpsize = ( int(psize / 4) + 1 ) * 4;

			for (int ch=0; ch<CH; ++ch) {	
				fin[ch]  = new float[factpsize];
				fout[ch] = new float[factpsize];
			}

			currentsize = psize;
		}

		*in = fin;
		*out = fout;

		return CH;
	}

	return 0;
}

static inline void swapfp(float*** a, float*** b) {
	float **s;
	s  = *a;
	*a = *b;
	*b = *a;
}

static void limitVolume(float* in, DWORD size) {
	static const float H = 0.5;
	static float step = 0.86;
	static float cut = 1;
	static clock_t usetime;
	
	for (DWORD i=0; i<size; ++i) {
		if (cut<1)
			in[i] *= cut;

		if ( in[i] > H ) {
			in[i] = H;
			cut = cut * step;
			usetime = clock();

		} else if ( in[i] < -H ) {
			in[i] = -H;
			cut = cut * step;
			usetime = clock();

		} else {
			if (cut<1) {
				if ( clock()-usetime>1300 ) {
					cut /= step;
					usetime = clock();
				}
			}
		}
	}

	if (cut<0.01) cut = 0.01;
}

// ÐÞ¸Ädata²¢·µ»Ødata
VSTPLUS LPBYTE processing(LPBYTE data, DWORD size) {
	if (closevts) return data;
	if (!(data && size && aecont)) return data;

	PtCh16bit pdata = (PtCh16bit)data;
	DWORD psize = size/4;

	float** fin;
	float** fout;
	bool swaped = FALSE;
	rebulidFloat(&fin, &fout, psize);

#ifdef SSEC
		__asm {
		}
#else
	// creat float array
	for (DWORD i=0; i<psize; i++) {
		fin[0][i] = (float)pdata[i].getR() / MAXV;
		fin[1][i] = (float)pdata[i].getL() / MAXV;
	}
#endif

	// filter
	AEffect* effect = NULL;
	for (int i=0; i<aecont; ++i) {
		effect = aeffects[i];
		effect->processReplacing (effect, fin, fout, psize);

		swapfp(&fin, &fout);
		swaped = !swaped;
	}

	if (swaped) {
		swapfp(&fin, &fout);
	}

	limitVolume( fout[0], psize );
	limitVolume( fout[1], psize );

#ifdef SSEC
		__asm {
		}
#else
	// remade int array
	for (DWORD i=0; i<psize; ++i) {
		pdata[i].setR( fout[0][i] * MAXV );
		pdata[i].setL( fout[1][i] * MAXV );
	}
#endif

	return data;
}

char* getAEName(AEffect* ae) {
	static char name[256];
	ae->dispatcher (ae, effGetEffectName, 0, 0, name, 0);
	return name;
}

AEffect* findAEffectOfName(char* name) {
	if (name) {
		for (int i=0; i<aecont; ++i) {
			if ( !strcmp( name, effname[i] ) ) {
				return aeffects[i];
			}
		}
	}
	return NULL;
}

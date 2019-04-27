// CatfoOD 2009

#include "aeffectx.h"
#include <stdio.h>
#include <windows.h>

struct MyDLGTEMPLATE: DLGTEMPLATE
{
	WORD ext[3];
	MyDLGTEMPLATE ()
	{
		memset (this, 0, sizeof (*this));
	};
};


bool checkEffectEditor (AEffect* effect[], int c);
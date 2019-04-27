// CatfoOD 2009.6.1

#include "stdafx.h"

typedef struct buff{
	bool			inuse;
	WAVEHDR			pwh;
	LPSTR			data;
	struct buff*	next;
} BUFF, *PBUFF;


static PBUFF start = NULL;
static PCRITICAL_SECTION lock = NULL;


void initBuffer() {
	if ( lock == NULL ) {
		lock = new CRITICAL_SECTION;
		InitializeCriticalSection( lock );
	}
}

void unloadBuffer() {
	PBUFF p = start;
	PBUFF n;
	if (start) {
		do {
			n = p->next;
			delete p;
		} while (n);
	}
	start = NULL;
}

static PBUFF creatNew(DWORD maxsize) {
	PBUFF b = new BUFF;
	b->inuse = FALSE;
	b->next = NULL;
	b->data = new CHAR[maxsize];
	b->pwh.lpData = b->data;
	return b;
}

static PBUFF findNotUse() {
	PBUFF pbf = start;
	while(pbf) {
		if (!pbf->inuse) return pbf;
		pbf = pbf->next;
	}
	return NULL;
}

static void addqueue(PBUFF newp) {
	PBUFF pbf = start;
	if (pbf) {
		while (pbf->next) pbf = pbf->next;
		pbf->next = newp;

	} else {
		start = newp;
	}
}

void freeWavehdr(LPWAVEHDR pwh) {
	//EnterCriticalSection( lock );

	PBUFF pdf = start;
	while(pdf) {
		if ( &pdf->pwh == pwh ) {
			pdf->inuse = FALSE;
			break;
		}
		pdf = pdf->next;
	} 

	//LeaveCriticalSection( lock );
}

void getSoundBuffer(LPWAVEHDR* pwh, DWORD maxsize) {
	//EnterCriticalSection( lock );

	PBUFF buff = findNotUse();
	if (!buff) {
		buff = creatNew(maxsize);
		addqueue(buff);
	}

	buff->inuse = TRUE;
	*pwh = &buff->pwh;

	//LeaveCriticalSection( lock );
}


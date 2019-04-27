#include "minieditor.h"


static INT_PTR CALLBACK EditorProc (HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam);
static AEffect** theEffect;
static HWND* hwnhadl;
static int effectcont = 0;
static int wndThread = 0;
static int resetwintitle = 0;
//static char titlename[80] = "Beoplayer Java VST Editor";


static AEffect* getHwndEffect(HWND h) {
	for (int i=0; i<effectcont; ++i) {
		if ( hwnhadl[i] == h ) {
			return theEffect[i];
		}
	}
	return NULL;
}

/*
void setDefaultWindTitle() {
	sprintf(titlename, VSTTITLE);
}

void setOutrangeVSTname(const char* name) {
	sprintf(titlename, "VST插件(%s)输出过载，请降低它的输出音量", name);
}
*/

//-------------------------------------------------------------------------------------------------------
bool checkEffectEditor (AEffect* effect[], int cont)
{
	if (effectcont) {
		wndThread = 0;
		return false;
	}

	theEffect = effect;
	hwnhadl   = new HWND[cont];
	effectcont= cont;

	for (int i=0; i<cont; ++i) {
		if ((effect[i]->flags & effFlagsHasEditor) == 0)
		{
			printf ("This plug does not have an editor!\n");
			continue;
		}

		MyDLGTEMPLATE t;	
		t.style = WS_POPUPWINDOW|WS_DLGFRAME|DS_MODALFRAME|DS_CENTER;
		t.cx = 100;
		t.cy = 100;
		hwnhadl[i] = CreateDialogIndirectParam(GetModuleHandle (0), &t, 0, (DLGPROC)EditorProc, (LPARAM)effect[i]);
		// CreateDialogIndirectParam  DialogBoxIndirectParam
	}

	MSG	msg;
	while (wndThread && GetMessage (&msg, NULL, 0, 0))
	{
		TranslateMessage (&msg) ;
		DispatchMessage (&msg) ;
	}
	printf("all edit win close.\n");

	delete[] hwnhadl;
	hwnhadl = 0;
	effectcont = 0;
	
	return true;
}

//-------------------------------------------------------------------------------------------------------

/*
VOID CALLBACK titleproc(HWND h, UINT i, UINT_PTR p, DWORD p2) {
	SetWindowTextA (h, titlename );
}
*/

INT_PTR CALLBACK EditorProc (HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
	char title[100];
	AEffect* effect;
	static int xoff = 0;

	switch(msg)
	{
		//-----------------------
		case WM_INITDIALOG :
		{
			effect = (AEffect*) lParam;

			if (wndThread<effectcont-1) 
				sprintf(title, "Jeoplayer Java VST %d号 输出到 %d号", wndThread+1, wndThread+2);
			else 
				sprintf(title, "Jeoplayer Java VST %d号 输出到系统", wndThread+1);

			SetWindowTextA (hwnd, title );
			SetTimer (hwnd, 1, 20, 0);

			if (effect)
			{
				printf ("HOST> Open editor...\n");
				effect->dispatcher (effect, effEditOpen, 0, 0, hwnd, 0);

				ERect* eRect = 0;
				effect->dispatcher (effect, effEditGetRect, 0, 0, &eRect, 0);
				if (eRect)
				{
					int width = eRect->right - eRect->left;
					int height = eRect->bottom - eRect->top;
					if (width < 100)
						width = 100;
					if (height < 100)
						height = 100;

					RECT wRect;
					SetRect (&wRect, 0, 0, width, height);
					AdjustWindowRectEx (&wRect, GetWindowLong (hwnd, GWL_STYLE), FALSE, GetWindowLong (hwnd, GWL_EXSTYLE));
					width = wRect.right - wRect.left;
					height = wRect.bottom - wRect.top;

					int ScreenHeight = GetSystemMetrics(SM_CYSCREEN);
					int ScreenWidth  = GetSystemMetrics(SM_CXSCREEN);
					int x, y;

					if ( effectcont>1 ) {
						if (wndThread && wndThread%3==0) xoff += 110;
						x = ScreenWidth/8 - 30*wndThread + xoff;
						y = ScreenHeight/6 + 15*wndThread;
					} else {
						x = (ScreenWidth - width)/2;
						y = (ScreenHeight- height)/2;
					}
					
					SetWindowPos (hwnd, HWND_TOP, x, y, width, height, SWP_SHOWWINDOW );// SWP_NOMOVE
				}
				wndThread++;
			}
		}	break;

		//-----------------------
		case WM_TIMER :
			effect = getHwndEffect(hwnd);
			if (effect) {
				effect->dispatcher (effect, effEditIdle, 0, 0, 0, 0);
			}
			break;

		//-----------------------
		case WM_CLOSE :
		{
			effect = getHwndEffect(hwnd);
			KillTimer (hwnd, 1);
			KillTimer (hwnd, 2);

			printf ("HOST> Close editor..\n");
			if (effect)
				effect->dispatcher (effect, effEditClose, 0, 0, 0, 0);

			EndDialog (hwnd, IDOK);
			wndThread--;
			xoff = 0;
		}	break;
	}

	return 0;
}

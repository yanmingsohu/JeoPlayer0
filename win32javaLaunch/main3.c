#include <windows.h>
#include <stdlib.h>
//#include <PROCESS.H>

void setupJava();

static char* env = "p=codec\\jflac-1.2.jar;codec\\jl1.0.jar;codec\\jmactritonusspi1.74.jar;"
                   "codec\\jogg-0.0.7.jar;codec\\jorbis-0.0.15.jar;codec\\mp3spi1.9.4.jar;"
                   "codec\\tritonus_share.jar;codec\\vorbisspi1.0.2.jar;";

static char* path = "path=%path%;codec;.";

int WINAPI WinMain (HINSTANCE hThisInstance,
                    HINSTANCE hPrevInstance,
                    LPSTR lpszArgument,
                    int nFunsterStil)
{
	int r = 0;
	putenv(path);
	r = WinExec("javaw -version:1.6 -cp %p%;.\\bin;codec\\* mainc", SW_SHOW);
	
    if (r==9009 || r<32) setupJava();
    
    return r;
}

void setupJava() {
	int r = 0;
	r = MessageBox(NULL, 
				"你的系统没有安装Java,是否立即下载并安装？", 
				"启动失败",
				MB_YESNO | MB_ICONQUESTION);
	
	if (r==IDYES) {
		WinExec("安装Java.exe", SW_SHOW);
	}
}


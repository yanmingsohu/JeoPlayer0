@echo off

set path=%path%;C:\Program Files\WinRAR;
cd..

del /q config\*

set /p beovarsion=����汾��=

rar a -r -m5 -x*test* -z[make]\self-release.txt [make]\JeoPlayer_v%beovarsion%.zip @[make]\filelist.txt

pause
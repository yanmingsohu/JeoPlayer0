@echo off

set ps=codec\

set p=%p%;%ps%jflac-1.2.jar
set p=%p%;%ps%jl1.0.jar
set p=%p%;%ps%jmactritonusspi1.74.jar
set p=%p%;%ps%jogg-0.0.7.jar
set p=%p%;%ps%jorbis-0.0.15.jar
set p=%p%;%ps%mp3spi1.9.4.jar
set p=%p%;%ps%tritonus_share.jar
set p=%p%;%ps%vorbisspi1.0.2.jar

set path=%path%;codec;.


rem java -version:1.6 -cp %p%;.\bin;codec\* mainc debug
java -cp %p%;.\bin;codec\* mainc debug


rem cls
echo.

if ERRORLEVEL 9009 (
	echo 找不到java.exe,请检查环境变量配置,
	echo 或安装最新的Java VM: http://java.sun.com
	goto end
)

echo.

if ERRORLEVEL 1 (
	if NOT EXIST bin\mainc.class (
		echo 程序文件安装不完整
		echo 详细情况与作者联系.
		goto end
	)
	
	echo 当前的Java版本:
	java -version
	echo.
	echo 运行JeoPlayer需要的最低版本: java version "1.6.0_03"
	echo.
	echo 请安装最新的Java VM: http://java.sun.com
	goto end
)

goto end
if ERRORLEVEL 0 (
	echo.
	echo 没有问题，可以运行
)

:end
echo.
echo	-------------------------------- CatfoOD 2008 ----------------------------------
echo	email: yanming-sohu@sohu.com
echo	QQ: 412475540
echo.
echo	Enjoyment.
echo.
echo.
echo.
echo.
echo.

echo 按任意键--退出...
pause > nul
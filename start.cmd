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
	echo �Ҳ���java.exe,���黷����������,
	echo ��װ���µ�Java VM: http://java.sun.com
	goto end
)

echo.

if ERRORLEVEL 1 (
	if NOT EXIST bin\mainc.class (
		echo �����ļ���װ������
		echo ��ϸ�����������ϵ.
		goto end
	)
	
	echo ��ǰ��Java�汾:
	java -version
	echo.
	echo ����JeoPlayer��Ҫ����Ͱ汾: java version "1.6.0_03"
	echo.
	echo �밲װ���µ�Java VM: http://java.sun.com
	goto end
)

goto end
if ERRORLEVEL 0 (
	echo.
	echo û�����⣬��������
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

echo �������--�˳�...
pause > nul
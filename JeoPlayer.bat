@echo off

set path=%path%;D:\Java\jre6\bin

set ps=codec\

set p=%p%;%ps%jflac-1.3-jdk1.4.jar
set p=%p%;%ps%jl1.0.jar
set p=%p%;%ps%jmactritonusspi1.74.jar
set p=%p%;%ps%jspeex0.9.7.jar
set p=%p%;%ps%jogg-0.0.7.jar
set p=%p%;%ps%jorbis-0.0.15.jar
set p=%p%;%ps%mp3spi1.9.4.jar
set p=%p%;%ps%tritonus_share.jar
set p=%p%;%ps%vorbisspi1.0.2.jar

start javaw -version:1.6 -cp %p%;.\bin;codec\* mainc

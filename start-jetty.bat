@echo off
REM Batch script to start Jetty server

REM Set Jetty Home and Base directories (update these paths if needed)
set JETTY_HOME=%~dp0jetty-home-12.1.5
set JETTY_BASE=%~dp0jetty-base

REM Set Java Home if needed (uncomment and update if required)
REM set JAVA_HOME=%~dp0environments\jdk-25
REM set PATH=%JAVA_HOME%\bin;%PATH%

REM Start Jetty
"%JETTY_HOME%\bin\jetty.bat" start

pause

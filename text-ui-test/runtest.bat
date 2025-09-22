@ECHO OFF

REM create bin directory if it doesn't exist
rmdir /s /q ..\bin
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
@echo off
set SRC=..\src\main\java
set OUT=..\bin

dir /s /b %SRC%\bob\*.java > sources.txt
javac -cp %SRC% -Xlint:none -d %OUT% @sources.txt
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin bob.Bob < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
del ACTUAL.TXT
del sources.txt
del data\Bob.txt
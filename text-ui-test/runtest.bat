@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder, recursively finding all .java files
javac -cp ..\src\main\java -Xlint:none -d ..\bin ^
    ..\src\main\java\songbird\*.java ^
    ..\src\main\java\songbird\command\*.java ^
    ..\src\main\java\songbird\exception\*.java ^
    ..\src\main\java\songbird\parser\*.java ^
    ..\src\main\java\songbird\task\*.java ^
    ..\src\main\java\songbird\ui\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin songbird.Songbird < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
IF ERRORLEVEL 1 (
    echo Test result: FAILED
    exit /b 1
) ELSE (
    echo Test result: PASSED
    exit /b 0
)
@echo off

set JAR_FILE=IgcViewer.jar

@set PATH=C:\Programok\cygwin\bin\;%PATH%

:: updating version info
C:\Programok\cygwin\bin\bash.exe -i -c "./version.sh"

:: updating source list
del /f /Q javafiles
C:\Programok\cygwin\bin\bash.exe -i -c "find src -iname *.java >javafiles"
mkdir bin
del /f /q /s *.class
::"C:\Program Files (x86)\Java\jdk1.7.0_11\bin\javac" -Werror -d bin -cp patzh-to-jar;path-to-jar2 @javafiles
"C:\Program Files (x86)\Java\jdk1.7.0_11\bin\javac" -Werror -d bin -cp sqlite-jdbc-3.27.2.1.jar @javafiles
if %ERRORLEVEL%==0 goto link_step
echo ERROR!
pause
exit 1

:link_step
cd bin
del /f /Q classfiles
C:\Programok\cygwin\bin\bash.exe -i -c "find . -iname *.class >classfiles"

del /f /Q %JAR_FILE%
"C:\Program Files (x86)\Java\jdk1.7.0_11\bin\jar" cmf ../manifest.mf %JAR_FILE% @classfiles
if %ERRORLEVEL%==0 goto copy_step
echo ERROR!
cd ..
pause
::exit 1

:copy_step
::del /f /q PATH\%JAR_FILE%
::copy %JAR_FILE% PATH
cd ..

:end

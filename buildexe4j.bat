@echo off
setlocal EnableDelayedExpansion
::ע�����Ӧ����ΪANSI

::�˴�����JDK�Ĵ��·��
set JAVA_HOME=D:\zulu11.43.55-ca-jdk11.0.9.1-win_x64

::�˴�����EXE4J�Ĵ��·��
set EXE4J_PATH=C:\Program Files\exe4j

set JAR_PATH=out\artifacts\earthquake_warning_jar
set JAR_FILE=earthquake-warning.jar

if not exist %JAR_PATH%\%JAR_FILE% (
echo δ�ҵ�JAR�ļ���
goto:eof
)

path %path%;%JAVA_HOME%\bin;%EXE4J_PATH%\bin
::����https://learn.microsoft.com/zh-cn/java/openjdk/java-security���ĵ�����Ҫ���jdk.crypto.ecģ��
set DEPS=jdk.crypto.ec
for /f %%i in ('jdeps --list-deps %JAR_PATH%\%JAR_FILE%') do set DEPS=!DEPS!,%%i
jlink --module-path "%JAVA_HOME%\jmods" --add-modules !DEPS! --output %JAR_PATH%\jre
exe4jc earthquake-warning.exe4j
del %JAR_PATH%\%JAR_FILE%
xcopy /e /y Files %JAR_PATH%\Files\
xcopy /e /y sounds %JAR_PATH%\sounds\
start %JAR_PATH%
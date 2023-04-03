@echo off
setlocal EnableDelayedExpansion
::注意编码应保存为ANSI

::此处设置JDK的存放路径
set JAVA_HOME=D:\zulu11.43.55-ca-jdk11.0.9.1-win_x64

::此处设置EXE4J的存放路径
set EXE4J_PATH=C:\Program Files\exe4j

set JAR_PATH=out\artifacts\earthquake_warning_jar
set JAR_FILE=earthquake-warning.jar

if not exist %JAR_PATH%\%JAR_FILE% (
echo 未找到JAR文件。
goto:eof
)

path %path%;%JAVA_HOME%\bin;%EXE4J_PATH%\bin
::根据https://learn.microsoft.com/zh-cn/java/openjdk/java-security的文档，需要添加jdk.crypto.ec模块
set DEPS=jdk.crypto.ec
for /f %%i in ('jdeps --list-deps %JAR_PATH%\%JAR_FILE%') do set DEPS=!DEPS!,%%i
jlink --module-path "%JAVA_HOME%\jmods" --add-modules !DEPS! --output %JAR_PATH%\jre
exe4jc earthquake-warning.exe4j
del %JAR_PATH%\%JAR_FILE%
xcopy /e /y Files %JAR_PATH%\Files\
xcopy /e /y sounds %JAR_PATH%\sounds\
start %JAR_PATH%
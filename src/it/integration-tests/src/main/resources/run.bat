@echo off
@rem this file is not used by build process
@rem the developer can use it to launch integration tests
set JAVA_OPTS=-Xms64m -Xmx64m
set MAVEN_OPTS=%JAVA_OPTS%
${maven.home}/bin/mvn.bat clean package
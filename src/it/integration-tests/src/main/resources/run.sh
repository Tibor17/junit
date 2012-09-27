#!/bin/sh
# this file is not used by build process
# the developer can use it to launch integration tests
export JAVA_OPTS=-Xms64m -Xmx64m
export MAVEN_OPTS=${JAVA_OPTS}
${maven.home}/bin/mvn.bat clean package
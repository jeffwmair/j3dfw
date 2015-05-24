#!/bin/bash
mvn package
mvn install:install-file -Dfile="target/j3dfw-1.0.jar" -DpomFile="pom.xml"

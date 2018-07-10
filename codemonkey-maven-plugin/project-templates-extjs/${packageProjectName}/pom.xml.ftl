<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
	    <groupId>com.codemonkey</groupId>
    	<artifactId>codemonkey</artifactId>
    	<version>3.0.0</version>
    	<relativePath>../codemonkey/pom.xml</relativePath>
	</parent>

	<artifactId>${packageProjectName}</artifactId>
	<packaging>pom</packaging>
	
	<modules>
	    <module>../codemonkey-core</module>
	    <module>../codemonkey-em</module>
	    <module>../codemonkey-shiro</module>
	    <module>../${packageProjectName}</module>
	</modules>
	
	<dependencies>
		
	</dependencies>
	
	<build>
	    <defaultGoal>install</defaultGoal>
	</build>
	
</project>

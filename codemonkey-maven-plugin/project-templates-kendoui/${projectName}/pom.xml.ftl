<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	
	<artifactId>${projectName}</artifactId>
	<packaging>jar</packaging>
	
	<parent>
	    <groupId>com.codemonkey</groupId>
	    <artifactId>${packageProjectName}</artifactId>
	    <version>3.0.0</version>
	    <relativePath>../${packageProjectName}/pom.xml</relativePath>
	</parent>
	
	<properties>
		<db.server>localhost</db.server>
		<db.name>${databaseName}</db.name>
		<jdbc.username>root</jdbc.username>
		<jdbc.password>root</jdbc.password>
	</properties>

	<dependencies>
	
		<dependency>
			<groupId>com.codemonkey</groupId>
			<artifactId>codemonkey-core</artifactId>
			<version>${r"${codemonkey.version}"}</version>
		</dependency>
		
		<dependency>
			<groupId>com.codemonkey</groupId>
			<artifactId>codemonkey-kendoui</artifactId>
			<version>${r"${codemonkey.version}"}</version>
		</dependency>
		
		<dependency>
			<groupId>com.codemonkey</groupId>
			<artifactId>codemonkey-shiro</artifactId>
			<version>${r"${codemonkey.version}"}</version>
		</dependency>
		
		<dependency>
			<groupId>com.codemonkey</groupId>
			<artifactId>codemonkey-maven-plugin</artifactId>
			<version>${r"${codemonkey.version}"}</version>
		</dependency>
		
		<dependency>
		    <groupId>org.sitemesh</groupId>
		    <artifactId>sitemesh</artifactId>
		    <version>3.0.0</version>
		</dependency>
		
		<dependency>
			<groupId>org.eclipse.jetty</groupId>
			<artifactId>jetty-servlets</artifactId>
		</dependency>
		
    	<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
        </dependency>
		
	</dependencies>
	
	<build>
		<defaultGoal>install</defaultGoal>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<mainClass>com.codemonkey.${SubPackageName}Application</mainClass>
				</configuration>
				<executions>
					<execution>
					    <phase>install</phase>
						<goals>
							<goal>repackage</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
</project>

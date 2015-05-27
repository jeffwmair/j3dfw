# j3dfw (Java 3D Framework)
Simple JOGL-based framework for 3d applications
## Maven Instructions
To configure your application to use this project, you need the following maven (pom.xml) configuration:

### The j3dfw artifact:
```xml
<dependency>
	<groupId>com.jwm.j3dfw</groupId>
	<artifactId>j3dfw</artifactId>
	<version>1.0</version>
</dependency>
```

### My public repository:
```xml
<repositories>
	<repository>
		<id>j3dfw-mvn-repo</id>
		<url>https://raw.github.com/jeffwmair/j3dfw/mvn-repo/</url>
		<snapshots>
			<enabled>true</enabled>
			<updatePolicy>always</updatePolicy>
		</snapshots>
	</repository>
</repositories> 
```

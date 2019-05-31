# waes-assignment-scalable
	This application has developed for WAES software developer assigment.

Assignment
	Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints

	<host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right

	The provided data needs to be diff-ed and the results shall be available on a third end point.

	<host>/v1/diff/<ID>

	The results shall provide the following info in JSON format

	If equal return that
	If not of equal size just return that
	If of same size provide insight in where the diffs are, actual diffs are not needed.
	So mainly offsets + length in the data
	Make assumptions in the implementation explicit, choices are good but need to be communicated assumptions in the implementation explicit, choices are good but need to be communicated

## Technologies(Frameworks and Plugins)
This project I have developed using Intellij IDE and these technologies and frameworks:

	-Java 8
    -Springboot,
    -Gradle,
    -Mysql Database,
    -H2 (memory database),
    -Swagger,
    -Lombok,
    -Actuator,
    -Docker,
	-PMD plugin,
	-Checkstyle,
    -Spring rest.

## About project	
	This project is formed per one SpringBoot Application.
        Notes about application:
            -It is configured to listen 8090 port;
            -It is configured to use Swagger, using it you can check the endpoints and payloads using an internet browser. To access Swagger interface use this url: http://localhost:8090/swagger-ui.html, just a detail, if you are using Docker in Windowns machine maybe you need to change the localhost to a -specific ip (I need to that because I have used Docker toolbox)
			-Basic the API has these endpoints:
				post - /v1/diff/{binaryDataId} - Diff right and left binary data;
                post - /v1/diff/{binaryDataId}/left - Input left binary data;
				post -	/v1/diff/{binaryDataId} - Diff right and left binary data.			
			-This project has Javadocs for every classes,
			-There are unit tests for service layer,
			-There are integration tests for API that simulate the complete flows:
				Insert data (left and right sides);
				Diff flow;
				These tests are configured to use H2 database, I mean the H2 is created just to use in integration tests, after it the H2 database instance will be destroyed.
			-This project is using PMD (https://maven.apache.org/plugins/maven-pmd-plugin/) and Checkstyle (https://maven.apache.org/plugins/maven-checkstyle-plugin/) plugins to keep a good quality in -the code.
			-During every build process, these process are executed:
				Execute unit tests for service layer
				Execute integration tests
				Execute Checkstyle verification
				Execute PMD verification	
				build jar file

## Run 
To run application without an IDE you need to follow these steps:
```bash
-Execute the Gradle build;
-To running this application you can running it without 
	 a profile, in this way the application will use H2 database,
	 if you want use Mysql database you just need to choose Mysql profile
	 (application-mysql.properties), to do that you need to configure the
	 application to use this profile (spring.profiles.active=mysql)
	 and configure the database connection in application-mysql.properties file,
	 if you want to use docker you need to configure it in profile file, you can find this configuration in docker-compose file (SPRING_PROFILES_ACTIVE);
	 -The application can create the database table structure every time that the application are started, if you want to do it, you need to discomment( remove #) in this line "spring.jpa.hibernate.ddl-auto=update" on application.properties.
```
Inside Intellij IDE:
```bash
-Import the project;
-Execute Gradle import;
-Check Enable annotation processing field in Intellij options
-If necessary configure Mysql database in profile file our if you want to use H2 you can pass to next step;
-Start application using the Intellij IDE.
```

## Docker
 To use docker you need follow these steps:
 ```bash
	-Build the application,
	-Build docker image with this command: docker build -t assignment-scalable-web . or docker build -t assignment-scalable-web . (you need to run this command in root project that you want to *create the docker image);
    -Execute the docker-compose file with this command: docker-compose up (you need to run this command in root project). You can -check if applications are running using the actuator feature, to do do that you need to access this url: http://localhost:8090/actuator/health;
```

## Database schema 
 The database schema is very simple, it is compost by this table:
```bash
	Binary_Data
		data_Id: int(11)
		left_Data: varchar(255)
		right_Data: varchar(255)
```

## Points to Improve 
 The database schema is very simple, it is compost by this table:
```bash
	- Api to Get Binary_Data;
	- Api to Delete Binary_Data;
	- Implement Spring security to give more security in the API.
```

If you have questions, please feel free to contact me.
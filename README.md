# Advertisement service on Twilio API (backend)

Steps to run service locally:

1. Java 11 JDK should be installed.

2. PostgreSQL 13 should be installed.

3. Maven 3+ should be installed. Run **mvn clean package** or **mvnw clean package** from root dir location

4. Run **mvn flyway:migrate**

5. After build will be finished type **java -jar -Dspring.profiles.active=dev target/twilio_backend.jar**
   to start service

6. After the service will be started open: **http://localhost:8080/**

It should return **Twilio API Service is UP**

Also you should see the Swagger page: **http://localhost:8080/swagger-ui.html**

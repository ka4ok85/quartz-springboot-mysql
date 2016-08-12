# quartz-springboot-mysql
Proof of concept for persisted Quartz using Spring Boot and MySQL

Based on davidkiss/spring-boot-quartz-demo

##How to run
1. Create "quartz" database in MySQL.
2. Set MySQL connection credentials in application-dev.properties
3. Run App
4. Open localhost:8080/api/start/{jobName}/{groupName}/{quantity}/{interval} in Browser (e.g. localhost:8080/api/start/job_1/group_1/10/5)
5. Confirm Service is called by Quartz. In console you should see "Hello World!" message(s)

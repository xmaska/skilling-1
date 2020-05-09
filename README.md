# skilling-1
Solution of Exercise 1

The installation is very simple, just import the project as a maven project and run test from its lifecycle. 
Main application class: DemoApplication.java
Test class: DemoApplicationTests.java

Some details of the implementation:
- Post endpoint implemented on the path /hello, expects a json body with "name" parameter. Returns a personalized greeting message.
- The status and uptime are provided by the spring boot actuator: https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html

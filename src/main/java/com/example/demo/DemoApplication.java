package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

@RestController
class Controller {
    @Autowired
    private Service service;

    @PostMapping(
            value = "/hello", consumes = "application/json", produces = "application/json")
    public String greetingPerson(@RequestBody Person person) {
        return service.greeting(person);
    }
}

@Component
class Service {

    public String greeting(Person person) {
        return "Hello " + person.name + "!";
    }

}

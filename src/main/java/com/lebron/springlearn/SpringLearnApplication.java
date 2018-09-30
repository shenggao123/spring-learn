package com.lebron.springlearn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@SpringBootApplication
@RestController @RequestScope
public class SpringLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLearnApplication.class, args);
    }


    @Autowired
    private String name;


    @RequestMapping("/test")
    public String test() {
        return name;
    }

    int i=0;

    @Bean
    @Scope("request")
    @RequestScope
    public String name() {
        return "name" + i++;
    }
}




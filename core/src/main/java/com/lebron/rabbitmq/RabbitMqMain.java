package com.lebron.rabbitmq;

import com.lebron.rabbitmq.config.RabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableRabbit
@RestController
public class RabbitMqMain {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMqMain.class);
//        ApplicationContext context = new SpringApplicationBuilder()
//                .sources(RabbitMqMain.class)
//                .web(false)
//                .run();
//        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
//        amqpTemplate.convertAndSend("Hello World");
//        System.out.println("Sent: Hello World");



    }

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("test")
    public String test() {
        amqpTemplate.convertAndSend("Hello World");
        return "send success";
    }


}

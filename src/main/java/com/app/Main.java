package com.app;

import com.app.infrastructure.api.routing.CarsRouting;
import com.app.infrastructure.api.routing.ErrorRouting;
import com.app.infrastructure.api.routing.SecurityRouting;
import com.app.infrastructure.api.routing.UserRouting;
import com.app.infrastructure.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static spark.Spark.initExceptionHandler;
import static spark.Spark.port;

public class Main {
    public static void main(String[] args) {
        initExceptionHandler(e -> System.out.println(e.getMessage()));
        port(8080);

        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        var errorRouting = context.getBean("errorRouting", ErrorRouting.class);
        errorRouting.routes();

        var securityRouting = context.getBean("securityRouting", SecurityRouting.class);
        securityRouting.routes();

        var carsRouting = context.getBean("carsRouting", CarsRouting.class);
        carsRouting.routes();

        var userRouting = context.getBean("userRouting", UserRouting.class);
        userRouting.routes();
    }
}
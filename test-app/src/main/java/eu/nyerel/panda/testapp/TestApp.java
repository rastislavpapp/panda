package eu.nyerel.panda.testapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
@SpringBootApplication
public class TestApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }

}
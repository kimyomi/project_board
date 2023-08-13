package com.board.facamboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FacamBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(FacamBoardApplication.class, args);
    }

}

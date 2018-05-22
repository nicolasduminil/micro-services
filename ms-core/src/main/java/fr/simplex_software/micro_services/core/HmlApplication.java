package fr.simplex_software.micro_services.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@RefreshScope
public class HmlApplication
{
    public static void main(String[] args) {
        SpringApplication.run(HmlApplication.class, args);
    }
}

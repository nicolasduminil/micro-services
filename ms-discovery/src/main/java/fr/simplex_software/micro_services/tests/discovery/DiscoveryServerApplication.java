package fr.simplex_software.micro_services.tests.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(DiscoveryServerApplication.class, args);
  }
}

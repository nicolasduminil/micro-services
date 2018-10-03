package fr.simplex_software.micro_services.core;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.context.config.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

@SpringBootApplication
@RefreshScope
public class HmlApplication
{
  @LoadBalanced
  @Bean
  public RestTemplate getRestTemplate()
  {
    return new RestTemplate();
  }

  public static void main(String[] args)
  {
    SpringApplication.run(HmlApplication.class, args);
  }
}

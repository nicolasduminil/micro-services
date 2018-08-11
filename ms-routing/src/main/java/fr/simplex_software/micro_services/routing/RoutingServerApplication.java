package fr.simplex_software.micro_services.routing;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.context.config.annotation.*;
import org.springframework.cloud.netflix.eureka.*;
import org.springframework.cloud.netflix.zuul.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

@SpringBootApplication
@EnableZuulProxy
@RefreshScope
public class RoutingServerApplication
{
  @LoadBalanced
  @Bean
  public RestTemplate getRestTemplate()
  {
    return new RestTemplate();
  }

  public static void main(String[] args)
  {
    SpringApplication.run(RoutingServerApplication.class, args);
  }
}
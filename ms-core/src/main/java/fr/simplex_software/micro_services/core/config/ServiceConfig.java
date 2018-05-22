package fr.simplex_software.micro_services.core.config;

import org.apache.activemq.*;
import org.apache.activemq.command.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jms.config.*;
import org.springframework.jms.core.*;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.net.*;

@Component
public class ServiceConfig
{
  @Value("${jms.destination.name}")
  private String destinationName;
  @Value("${jms.broker.url}")
  private URI jmsBrokerURL;

  @Bean
  public ConnectionFactory jmsConnectionFactory()
  {
    ActiveMQConnectionFactory acf = new ActiveMQConnectionFactory(jmsBrokerURL);
    acf.setTrustAllPackages(true);
    return acf;
  }

  @Bean
  public JmsTemplate jmsTopicTemplate()
  {
    JmsTemplate jmsTemplate = new JmsTemplate(jmsConnectionFactory());
    jmsTemplate.setDefaultDestinationName(destinationName);
    jmsTemplate.setPubSubDomain(true);
    return jmsTemplate;
  }

  @Bean
  public Topic hmlGlobalTopic()
  {
    return new ActiveMQTopic(destinationName);
  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory()
  {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(jmsConnectionFactory());
    factory.setConcurrency("1-1");
    factory.setPubSubDomain(true);
    return factory;
  }

  public String getDestinationName()
  {
    return destinationName;
  }

  public URI getJmsBrokerURL()
  {
    return jmsBrokerURL;
  }
}

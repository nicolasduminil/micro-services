package fr.simplex_software.micro_services.core.controllers;

import fr.simplex_software.micro_services.core.domain.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.netflix.eureka.*;
import org.springframework.http.*;
import org.springframework.jms.core.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

import javax.jms.*;
import java.net.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class HmlRestController
{
  public static final Logger logger = LoggerFactory.getLogger(HmlRestController.class);

  @Autowired
  private JmsTemplate jmsTemplate;
  @Autowired
  public Topic hmlGlobalTopic;
  @Autowired
  private DiscoveryClient discoveryClient;

  private static HashMap<String, JmsTopicSubscriberInfo> subscribers = new HashMap<String, JmsTopicSubscriberInfo>();

  @RequestMapping(value = "/publish/", method = RequestMethod.POST)
  public ResponseEntity<HmlEvent> gtsPublish(@RequestBody HmlEvent hmlEvent)
  {
    jmsTemplate.convertAndSend(hmlGlobalTopic, hmlEvent);
    logger.debug("*** HmlRestController.publish(): Have published a new event {} {}", hmlEvent.getMessageId(), hmlEvent.getPayload());
    return ResponseEntity.accepted().body(hmlEvent);
  }

  @RequestMapping(value = "/subscribe/", method = RequestMethod.POST)
  public ResponseEntity gtsSubscribe(@RequestBody SubscriberInfo si) throws JMSException
  {
    subscribers.put(si.getSubscriptionName(), si.getSubscriberInfo());
    logger.debug("*** HmlRestController.gtsSubscribe(): Have subscribed to events {}, {}, {}", si.getSubscriptionName(), si.getSubscriberInfo().getClientId(), si.getSubscriberInfo().getMessageSelector());
    return ResponseEntity.accepted().build();
  }

  @RequestMapping(value = "/test/", method = RequestMethod.POST)
  public ResponseEntity gtsTest(@RequestBody HmlEvent event)
  {
    logger.debug("*** HmlRestController.gtsTest: Have received an event {}, {}", event.getMessageId(), event.getPayload());
    return ResponseEntity.accepted().build();
  }

  @RequestMapping(value = "/services", method = RequestMethod.GET)
  public List<String> getRegisteredServices()
  {
    logger.debug("*** HmlRestController.getRegisteredService()");
    List<String> services = new ArrayList<String>();
    discoveryClient.getServices().forEach(serviceName ->
    {
      discoveryClient.getInstances(serviceName).forEach(instance ->
      {
        services.add(String.format("%s:%s", serviceName, instance.getUri()));
      });
    });
    return services;
  }

  public static String getCallbackUrl(String subscriptionName)
  {
    return subscribers.get(subscriptionName).getCallback();
  }
}

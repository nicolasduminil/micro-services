package fr.simplex_software.micro_services.core.controllers;

import fr.simplex_software.micro_services.core.domain.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.http.*;
import org.springframework.jms.core.*;
import org.springframework.web.bind.annotation.*;

import javax.jms.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class HmlRestController
{
  private static final Logger logger = LoggerFactory.getLogger(HmlRestController.class);

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
  public ResponseEntity gtsSubscribe(@RequestBody SubscriberInfo si)
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
    logger.debug("*** HmlRestController.getRegisteredServices()");
    List<String> services = new ArrayList<String>();
    discoveryClient.getServices().forEach(serviceName -> discoveryClient.getInstances(serviceName).forEach(instance -> services.add(String.format("%s:%s", serviceName, instance.getUri()))));
    return services;
  }

  public static String getCallbackUrl(String subscriptionName)
  {
    JmsTopicSubscriberInfo jtsi = subscribers.get(subscriptionName);
    return jtsi == null ? "": jtsi.getCallback();
  }

  private ResponseEntity gtsFallbackSubscribe(@RequestBody SubscriberInfo si) throws JMSException
  {
    logger.debug("*** HmlRestController.gtsFallbackSubscribe(): No subscribe service available");
    return ResponseEntity.accepted().build();
  }
}

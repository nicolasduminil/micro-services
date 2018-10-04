package fr.simplex_software.micro_services.core.listeners;

import fr.simplex_software.micro_services.core.controllers.*;
import fr.simplex_software.micro_services.core.domain.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.http.*;
import org.springframework.jms.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import javax.jms.*;

@Component
public class HmlMessageListener
{
  public static final Logger logger = LoggerFactory.getLogger(HmlMessageListener.class);
  private JmsTopicSubscriberInfo jtsi;
  @Autowired
  @LoadBalanced
  private RestTemplate restTemplate;

  public HmlMessageListener()
  {
  }

  public HmlMessageListener(JmsTopicSubscriberInfo jtsi)
  {
    this.jtsi = jtsi;
  }

  public JmsTopicSubscriberInfo getJtsi()
  {
    return jtsi;
  }

  public void setJtsi(JmsTopicSubscriberInfo jtsi)
  {
    this.jtsi = jtsi;
  }

  @JmsListener(destination = "#{@hmlConfig.destinationName}")
  public void onMessage(Message message) throws JMSException
  {
    logger.debug("*** HmlMessageListener.onMessage: have received a message {}", message);
    HmlEvent event = (HmlEvent)((ObjectMessage)message).getObject();
    String subscriptionName = event.getSubscriptionName();
    String callbackUrl = null;
    //
    // Avoid to publish before subscribing.
    // This may happen sometimes, due to the the services latency relative to the JMS listener
    //
    while  ((callbackUrl = HmlRestController.getCallbackUrl(subscriptionName)).isEmpty())
      Thread.yield();
    logger.debug("*** HmlMessageListener.onMessage: Invoking {}", callbackUrl);
    restTemplate.postForObject(callbackUrl, new HttpEntity<HmlEvent>(event), Message.class);
  }
}

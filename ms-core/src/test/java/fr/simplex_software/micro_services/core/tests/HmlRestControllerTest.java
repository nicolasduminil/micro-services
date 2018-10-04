package fr.simplex_software.micro_services.core.tests;

import fr.simplex_software.micro_services.core.*;
import fr.simplex_software.micro_services.core.domain.*;
import org.junit.*;
import org.junit.runner.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.netflix.eureka.*;
import org.springframework.http.*;
import org.springframework.test.context.junit4.*;
import org.springframework.web.client.*;

import java.net.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmlApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableEurekaClient
public class HmlRestControllerTest
{
  @Autowired
  @LoadBalanced
  private RestTemplate restTemplate;
  private static Logger slf4jLogger = LoggerFactory.getLogger(HmlRestControllerTest.class);

  @Test
  public void test1() throws MalformedURLException
  {
    assertNotNull(restTemplate);
    HttpEntity<SubscriberInfo> request = new HttpEntity<>(new SubscriberInfo("subscriptionName",
      new JmsTopicSubscriberInfo("selector", "clientId", "http://hml-routing/hml-core/api/test/")));
    slf4jLogger.debug("*** RestTemplate.test1(): ready to subscribe");
    ResponseEntity resp = restTemplate.postForEntity("http://hml-routing/hml-core/api/subscribe/", request, Void.class);
    slf4jLogger.debug("*** RestTemplate.test1(): Have subscribed, status code {}", resp.getStatusCode());
    assertEquals(resp.getStatusCode(), HttpStatus.ACCEPTED);
    HttpEntity<HmlEvent> request2 = new HttpEntity<>(new HmlEvent("subscriptionName", "messageId", "payload"));
    slf4jLogger.debug("*** RestTemplate.test1(): ready to publish");
    HmlEvent hmle = restTemplate.postForObject("http://hml-routing/hml-core/api/publish/", request2, HmlEvent.class);
    slf4jLogger.debug("*** RestTemplate.test1(): Have published, event {}", hmle);
    assertNotNull(hmle);
    assertEquals(hmle.getMessageId(), "messageId");
    assertEquals(hmle.getPayload(), "payload");
  }
}

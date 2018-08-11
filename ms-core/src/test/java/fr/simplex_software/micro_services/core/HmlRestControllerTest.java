package fr.simplex_software.micro_services.core;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import fr.simplex_software.micro_services.core.domain.*;
import org.junit.*;
import org.junit.rules.*;
import org.junit.runner.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.web.client.*;
import org.springframework.cloud.client.circuitbreaker.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.netflix.eureka.*;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.*;
import org.springframework.web.client.*;

import java.net.*;
import java.rmi.*;
import java.util.*;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmlApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableEurekaClient
public class HmlRestControllerTest
{
  public static final Logger logger = LoggerFactory.getLogger(HmlRestControllerTest.class);

  @Autowired
  @LoadBalanced
  private RestTemplate restTemplate;

  @Test
  public void test1() throws MalformedURLException, JsonProcessingException
  {
    assertNotNull(restTemplate);
    ResponseEntity<List<String>> services = restTemplate.exchange("http://hml-core/api/services", HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>()
    {
    });
    services.getBody().forEach(service ->
    {
      logger.debug("*** Registered service: {}", service);

    });
    HttpEntity<SubscriberInfo> request = new HttpEntity<SubscriberInfo>(new SubscriberInfo("subscriptionName",
      new JmsTopicSubscriberInfo("selector", "clientId", "http://hml-routing/hml-core/api/test/")));
    ResponseEntity resp = restTemplate.postForEntity("http://hml-routing/hml-core/api/subscribe/", request, Void.class);
    assertEquals(resp.getStatusCode(), HttpStatus.ACCEPTED);
    HttpEntity<HmlEvent> request2 = new HttpEntity<>(new HmlEvent("subscriptionName", "messageId", "payload"));
    HmlEvent hmle = restTemplate.postForObject("http://hml-routing/hml-core/api/publish/", request2, HmlEvent.class);
    assertThat(hmle, notNullValue());
    assertEquals(hmle.getMessageId(), "messageId");
    assertEquals(hmle.getPayload(), "payload");
  }
}

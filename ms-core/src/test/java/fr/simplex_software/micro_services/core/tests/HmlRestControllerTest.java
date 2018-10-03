package fr.simplex_software.micro_services.core.tests;

import com.fasterxml.jackson.core.*;
import fr.simplex_software.micro_services.core.*;
import fr.simplex_software.micro_services.core.domain.*;
import org.junit.*;
import org.junit.runner.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.netflix.eureka.*;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.test.context.junit4.*;
import org.springframework.web.client.*;

import java.net.*;
import java.util.*;

import static org.hamcrest.core.IsNull.*;
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
    HttpEntity<SubscriberInfo> request = new HttpEntity<SubscriberInfo>(new SubscriberInfo("subscriptionName",
      new JmsTopicSubscriberInfo("selector", "clientId", "http://hml-routing/hml-core/api/test/")));
    ResponseEntity resp = restTemplate.postForEntity("http://hml-routing/hml-core/api/subscribe/", request, Void.class);
    assertEquals(resp.getStatusCode(), HttpStatus.OK);
    HttpEntity<HmlEvent> request2 = new HttpEntity<>(new HmlEvent("subscriptionName", "messageId", "payload"));
    HmlEvent hmle = restTemplate.postForObject("http://hml-routing/hml-core/api/publish/", request2, HmlEvent.class);
    if (hmle != null)
    {
      assertEquals(hmle.getMessageId(), "messageId");
      assertEquals(hmle.getPayload(), "payload");
    }
  }
}

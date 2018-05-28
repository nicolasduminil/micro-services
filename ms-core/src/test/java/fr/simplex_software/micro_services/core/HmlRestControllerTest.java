package fr.simplex_software.micro_services.core;

import fr.simplex_software.micro_services.core.domain.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.context.junit4.*;
import org.springframework.web.client.*;

import java.net.*;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmlApplication.class)
public class HmlRestControllerTest
{
  private RestTemplate restTemplate = new RestTemplate();

  @Test
  public void test1() throws MalformedURLException
  {
    HttpEntity<SubscriberInfo> request = new HttpEntity<SubscriberInfo>(new SubscriberInfo("subscriptionName",
      new JmsTopicSubscriberInfo("selector", "clientId", "http://localhost:8080/api/test/")));
    ResponseEntity resp = restTemplate.postForEntity("http://localhost:8080/api/subscribe/", request, Void.class);
    assertEquals(resp.getStatusCode(), HttpStatus.ACCEPTED);
    HttpEntity<HmlEvent> request2 = new HttpEntity<>(new HmlEvent("subscriptionName", "messageId", "payload"));
    HmlEvent hmle = restTemplate.postForObject("http://localhost:8080/api/publish/", request2, HmlEvent.class);
    assertThat(hmle, notNullValue());
    assertEquals(hmle.getMessageId(), "messageId");
    assertEquals(hmle.getPayload(), "payload");
  }
}

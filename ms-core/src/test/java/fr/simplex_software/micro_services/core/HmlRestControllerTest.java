package fr.simplex_software.micro_services.core;

import fr.simplex_software.micro_services.core.domain.*;
import org.junit.*;
import org.junit.runner.*;
import org.keycloak.admin.client.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.*;
import org.springframework.web.client.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.net.*;

import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmlApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HmlRestControllerTest
{
  private Logger slf4jLogger = LoggerFactory.getLogger(HmlRestControllerTest.class);
  @Autowired
  @LoadBalanced
  private RestTemplate restTemplate;
  private String strUrl;

  @Before
  public void setup()
  {
    strUrl = "http://" + System.getProperty("keycloakIPAddress") + ":8080/auth";
    slf4jLogger.debug ("*** HmlRestControllerTest.setup(): strUrl {}", strUrl);
  }

  @Test
  public void test1() throws MalformedURLException
  {
    String token = Keycloak.getInstance(strUrl, "master", "customer-admin", "admin", "curl").tokenManager().getAccessToken().getToken();
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    /*Client client = ClientBuilder.newClient();
    WebTarget webTarget = client.target("http://localhost:8080/api");*/
    HttpEntity<SubscriberInfo> request = new HttpEntity<SubscriberInfo>(new SubscriberInfo("subscriptionName",
      new JmsTopicSubscriberInfo("selector", "clientId", "http://localhost:8080/api/test/")),headers);
    /*SubscriberInfo subscriberInfo = new SubscriberInfo("subscriptionName",
      new JmsTopicSubscriberInfo("selector", "clientId", "http://localhost:8080/api/test/"));
    Response response = webTarget.path("subscribe").request().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).post(Entity.entity(subscriberInfo, "application/json"));*/
    ResponseEntity resp = restTemplate.postForEntity("http://localhost:8080/api/subscribe/", request, Void.class);
    assertEquals(resp.getStatusCode(), HttpStatus.ACCEPTED);
    //assertEquals(response.getStatus(), HttpStatus.ACCEPTED);
    HttpEntity<HmlEvent> request2 = new HttpEntity<>(new HmlEvent("subscriptionName", "messageId", "payload"));
    HmlEvent hmle = restTemplate.postForObject("http://localhost:8080/api/publish/", request2, HmlEvent.class);
    /*HmlEvent hmlEvent = new HmlEvent("subscriptionName", "messageId", "payload");
    response = webTarget.path("publish").request().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).post(Entity.entity(hmlEvent, "application/json"));*/
    assertThat(hmle, notNullValue());
    assertEquals(hmle.getMessageId(), "messageId");
    assertEquals(hmle.getPayload(), "payload");
  }
}

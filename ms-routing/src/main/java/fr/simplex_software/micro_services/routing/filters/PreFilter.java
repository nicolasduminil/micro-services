package fr.simplex_software.micro_services.routing.filters;

import com.netflix.zuul.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class PreFilter extends ZuulFilter
{
  private static final Logger logger = LoggerFactory.getLogger(PreFilter.class);
  private String token = null;
  @Value("${keycloak.auth-server-url}")
  private String authServerUrl;
  @Value("${security.role")
  private String securityRole;
  @Value("${security.user}")
  private String securityUser;
  @Value("${security.password}")
  private String securityPassword;
  @Value("${security.client-id}")
  private String securityClientId;

  @Override
  public String filterType()
  {
    return "pre";
  }

  @Override
  public int filterOrder()
  {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  public Object run()
  {
    /*RequestContext requestContext = RequestContext.getCurrentContext();
    logger.error("*** in zuul filter " + requestContext.getRequest().getRequestURI());
    requestContext.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, "Bearer" + getToken());
    Map<String, String> headers = requestContext.getZuulRequestHeaders();
    for (Map.Entry<String, String> entry : requestContext.getZuulRequestHeaders().entrySet())
      logger.error ("*** {}->{}", entry.getKey(), entry.getValue());*/
    return null;
  }

  /*private String getToken()
  {
    if (token == null)
      token = Keycloak.getInstance(authServerUrl, securityRole, securityUser, securityPassword, securityClientId).tokenManager().getAccessToken().getToken();
    return token;
  }*/
}

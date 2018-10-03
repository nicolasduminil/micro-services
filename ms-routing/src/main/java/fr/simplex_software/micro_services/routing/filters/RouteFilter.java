package fr.simplex_software.micro_services.routing.filters;

import com.netflix.zuul.*;
import com.netflix.zuul.context.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import javax.servlet.http.*;

@Component
public class RouteFilter extends ZuulFilter
{
  private static final Logger logger = LoggerFactory.getLogger(RouteFilter.class);
  private static final String AUTHORIZATION_HEADER = "authorization";
  private static Logger slf4jLogger = LoggerFactory.getLogger(RouteFilter.class);
  private static String accessToken;
  @Autowired
  private RestTemplate restTemplate;

  @Override
  public String filterType()
  {
    return "pre";
  }

  @Override
  public int filterOrder()
  {
    return 2;
  }

  @Override
  public boolean shouldFilter()
  {
    return true;
  }

  public Object run()
  {
    HttpServletRequest req = RequestContext.getCurrentContext().getRequest();
    logger.debug("*** RouteFilter(): Processing incoming request for {}, {}, {}.", req.getRequestURI(), req.getRequestURL().toString(), req.getRemoteAddr());
    //if (accessToken == null)
     accessToken = restTemplate.getForObject("http://hml-keycloak/auth/accessToken", String.class);
    logger.debug("*** RouteFilter(): Processing outgoing response for {}.", accessToken);
    RequestContext.getCurrentContext().addZuulRequestHeader(AUTHORIZATION_HEADER, "Bearer " + accessToken);
    return null;
  }
}

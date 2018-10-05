package fr.simplex_software.micro_services.routing.filters;

import com.netflix.zuul.*;
import com.netflix.zuul.context.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import javax.servlet.http.*;

@Component
public class RouteFilter extends ZuulFilter
{
  private static final Logger logger = LoggerFactory.getLogger(RouteFilter.class);
  private static final String AUTHORIZATION_HEADER = "authorization";
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
    RequestContext context = RequestContext.getCurrentContext();
    HttpServletRequest req = context.getRequest();
    logger.debug("*** RouteFilter(): Processing incoming request for {}, {}, {}.", req.getRequestURI(), req.getRequestURL().toString(), req.getRemoteAddr());
    accessToken = restTemplate.getForObject("http://hml-keycloak/auth/accessToken", String.class);
    logger.debug("*** RouteFilter(): Processing outgoing response for {}.", accessToken);
    context.addZuulRequestHeader(AUTHORIZATION_HEADER, "Bearer " + accessToken);
    return null;
  }
}

package fr.simplex_software.micro_services.routing.filters;

import com.netflix.zuul.*;
import com.netflix.zuul.context.*;
import org.slf4j.*;
import org.springframework.stereotype.*;

@Component
public class RouteFilter extends ZuulFilter
{
  private static final Logger logger = LoggerFactory.getLogger(RouteFilter.class);

  @Override
  public String filterType()
  {
    return "route";
  }

  @Override
  public int filterOrder()
  {
    return 2;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  public Object run()
  {
    logger.debug("*** RouteFilter(): Processing incoming request for {}.", RequestContext.getCurrentContext().getRequest().getRequestURI());
    return null;
  }
}

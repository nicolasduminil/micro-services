package fr.simplex_software.micro_services.routing.filters;

import com.netflix.zuul.*;
import com.netflix.zuul.context.*;
import org.slf4j.*;
import org.springframework.stereotype.*;

@Component
public class PreFilter extends ZuulFilter
{
  private static final Logger logger = LoggerFactory.getLogger(PreFilter.class);

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
    logger.debug("*** PreFilter(): Processing incoming request for {}.", RequestContext.getCurrentContext().getRequest().getRequestURI());
    return null;
  }
}

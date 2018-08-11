package fr.simplex_software.micro_services.routing.filters;

import com.netflix.zuul.*;
import com.netflix.zuul.context.*;
import org.slf4j.*;
import org.springframework.stereotype.*;

@Component
public class PostFilter extends ZuulFilter
{
  private static final Logger logger = LoggerFactory.getLogger(PostFilter.class);

  @Override
  public String filterType()
  {
    return "post";
  }

  @Override
  public int filterOrder()
  {
    return 3;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  public Object run()
  {
    logger.debug("*** PostFilter(): Processing incoming request for {}.", RequestContext.getCurrentContext().getRequest().getRequestURI());
    return null;
  }
}

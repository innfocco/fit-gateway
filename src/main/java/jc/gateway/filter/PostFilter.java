package jc.gateway.filter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import jc.gateway.conf.FitHeaders;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class PostFilter extends ZuulFilter {

	  @Override
	  public String filterType() {
	    return "post";
	  }

	  @Override
	  public int filterOrder() {
	    return 1;
	  }

	  @Override
	  public boolean shouldFilter() {
	    return RequestContext.getCurrentContext().get(FitHeaders.XFaultInjectionID) == null?true:false;
	  }

	  @Override
	  public Object run() {
	    HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
	    log.info("PostFilter: " + String.format("response's content type is %s", response.getStatus()));
	    return null;
	  }
}
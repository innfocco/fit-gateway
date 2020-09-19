package jc.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import jc.gateway.conf.FitHeaders;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class RouteFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return "route";
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
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info("RouteFilter: " + String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
		return null;
	}
}
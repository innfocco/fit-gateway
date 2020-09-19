package jc.gateway.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import jc.gateway.conf.FitHeaders;
import jc.gateway.entity.Behavior;
import jc.gateway.svc.BehaviorService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class PreFilter extends ZuulFilter {
	
	private BehaviorService svc;
	
	public PreFilter(BehaviorService svc) {
		this.svc = svc;
	}

	  @Override
	  public String filterType() {
	    return "pre";
	  }

	  @Override
	  public int filterOrder() {
	    return 1;
	  }

	  @Override
	  public boolean shouldFilter() {
	    return true;
	  }

	  @Override
	  public Object run() {
	    RequestContext ctx = RequestContext.getCurrentContext();
	    HttpServletRequest request = ctx.getRequest();
	    String fitId = request.getHeader(FitHeaders.XFaultInjectionID);
	    Behavior behavior = null;
	    if(fitId != null) {
	    	behavior = svc.read(fitId);
	    }
		if (behavior != null && request.getRequestURI().equalsIgnoreCase(behavior.getRoute())) {
			injectFault(ctx, behavior);
		}
	    log.info("PreFilter: " + String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
	    return null;
	  }

	private void injectFault(RequestContext ctx, Behavior behavior) {
		HttpServletResponse res = ctx.getResponse();
		if(behavior.getHeadersResponse() != null) {
			for(String headerKey : behavior.getHeadersResponse().keySet()) {
				res.addHeader(headerKey, behavior.getHeadersResponse().get(headerKey));
			}
		}
		delay(behavior);
		res.setContentType("application/json");
	    res.setCharacterEncoding("UTF-8");
		if(behavior.getHttpStatus() > 399) {
			ctx.set(FitHeaders.XFaultInjectionID);
			ctx.set("BehaviorAsException", behavior);
		} else {
			res.setStatus(behavior.getHttpStatus());
		}
		if(behavior.getPayloadResponse() != null) {
			ctx.set(FitHeaders.XFaultInjectionID);
			ctx.setResponseBody(behavior.getPayloadResponse());
		}
	}

	private void delay(Behavior behavior) {
		if(behavior.getDelay() != null) {
			try {
				Thread.sleep(behavior.getDelay());
			} catch (InterruptedException e) {}
		}
	}
}
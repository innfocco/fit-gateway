package jc.gateway.filter;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import jc.gateway.entity.Behavior;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ErrorFilter extends ZuulFilter {

	protected static final String SEND_ERROR_FILTER_RAN = "sendErrorFilter.ran";

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return -1;
	}

	@Override
	public boolean shouldFilter() {
		return RequestContext.getCurrentContext().containsKey("BehaviorAsException");
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		Behavior behavior = (Behavior) ctx.get("BehaviorAsException");
		log.info("Injecting Error Response: {}", behavior);
		ctx.remove("error.status_code");
		ctx.setResponseBody(behavior.getPayloadResponse());
		ctx.getResponse().setContentType("application/json");
		ctx.setResponseStatusCode(behavior.getHttpStatus()); 
        return null;
	}
}
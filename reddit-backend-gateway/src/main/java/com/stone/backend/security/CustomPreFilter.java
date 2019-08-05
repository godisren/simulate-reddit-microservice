package com.stone.backend.security;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.context.SecurityContextHolder;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class CustomPreFilter extends ZuulFilter {

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		
		try {
			String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ctx.addZuulRequestHeader("username", username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// remove throwable to avoid sendErrorFilter triggered 
		ctx.remove("throwable");
		
		return null;
	}
	
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();	
		return ctx.getRequest().getHeader("Authorization") != null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER+1;
	}

}

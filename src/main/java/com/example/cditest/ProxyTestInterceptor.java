package com.example.cditest;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

public class ProxyTestInterceptor {
	@Inject
	Logger logger;

	public static String testStringProxyReturnValue = "TEST";

	@AroundInvoke
	public Object handle(InvocationContext ctx) throws Exception {
		logger.info("Aroundinvoke for method " + ctx.getMethod().getName());

		if ("test".equals(ctx.getMethod().getName())
				&& String.class.equals(ctx.getMethod().getReturnType())) {
			return testStringProxyReturnValue;
		}

		return ctx.proceed();
	}
}

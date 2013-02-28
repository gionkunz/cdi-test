package com.example.cditest;

import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.jboss.solder.serviceHandler.ServiceHandlerExtension;

public class ProxyTestExtension extends ServiceHandlerExtension {
	@Override
	protected <X> Class<?> getHandlerClass(ProcessAnnotatedType<X> event) {
		if (event.getAnnotatedType().isAnnotationPresent(Callable.class)) {
			return ProxyTestInterceptor.class;
		}
		return null;
	}
}

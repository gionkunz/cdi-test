package com.example.cditest;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class LoggerProducer {

	@Produces
	public Logger produceLogger(InjectionPoint ip) {
		if (!Logger.getRootLogger().getAllAppenders().hasMoreElements()) {
			Logger root = Logger.getRootLogger();
			root.addAppender(new ConsoleAppender(new PatternLayout(
					PatternLayout.TTCC_CONVERSION_PATTERN)));
		}

		if(ip != null) {
			return Logger.getLogger(ip.getMember().getDeclaringClass());
		} else {
			return Logger.getLogger(LoggerProducer.class);
		}
	}
}

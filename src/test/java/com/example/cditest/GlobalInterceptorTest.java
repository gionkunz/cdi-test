package com.example.cditest;

import static org.fest.assertions.Assertions.assertThat;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class GlobalInterceptorTest {
	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClasses(ProxyTestInterceptor.class,
						ProxyTestExtension.class, ProxyTestInterface.class, LoggerProducer.class)
				.addAsManifestResource(
						"META-INF\\services\\javax.enterprise.inject.spi.Extension",
						"META-INF\\services\\javax.enterprise.inject.spi.Extension")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	Instance<Logger> logger;

	@Inject
	Instance<ProxyTestInterface> proxyTest;

	@Test
	public void loggerInjectionTest() {
		assertThat(logger).isNotNull();
		assertThat(logger.isAmbiguous()).isFalse();
		assertThat(logger.isUnsatisfied()).isFalse();
		assertThat(logger.get().getName()).isEqualTo(
				GlobalInterceptorTest.class.getName());
	}

	@Test
	public void testProxyIntercept() {
		assertThat(proxyTest).isNotNull();
		assertThat(proxyTest.isAmbiguous()).isFalse();
		assertThat(proxyTest.isUnsatisfied()).isFalse();
		assertThat(proxyTest.get().test()).isEqualTo(
				ProxyTestInterceptor.testStringProxyReturnValue);
	}
}

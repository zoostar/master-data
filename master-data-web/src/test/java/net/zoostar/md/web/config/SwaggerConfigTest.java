package net.zoostar.md.web.config;

import org.junit.Assert;
import org.junit.Test;

import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfigTest {

	@Test
	public void testGetApi() {
		SwaggerConfig config = new SwaggerConfig();
		config.setRequestHandlerSelectorsBasePackage("net.zoostar.md.web.controller.api");
		Docket docket = config.getApi();
		Assert.assertNotNull(docket);
	}

}

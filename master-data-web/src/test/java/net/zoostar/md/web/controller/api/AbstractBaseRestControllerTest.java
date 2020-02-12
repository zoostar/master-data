package net.zoostar.md.web.controller.api;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/applicationContext.xml", "classpath*:META-INF/master-data-jobs.xml",
		"classpath:META-INF/applicationContext-test.xml", "classpath:META-INF/datasource.xml"})
public abstract class AbstractBaseRestControllerTest {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
}

package net.zoostar.md.web.controller.api;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;
import net.zoostar.md.model.Product;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/applicationContext.xml", "classpath*:META-INF/master-data-jobs.xml",
		"classpath:META-INF/applicationContext-test.xml", "classpath:META-INF/datasource.xml"})
public class ProductRestControllerTest {

	protected ProductRestController productService;
	
	@Autowired
	public void setProductService(ProductRestController productService) {
		this.productService = productService;
	}
	
	@Test
	public void testCreate() {
		Assert.assertNotNull(productService);
		Product product = new Product();
		product.setAssetId("assetId");
		product.setDesc("desc");
		product.setSku("sku");
		ResponseEntity<Product> response = productService.create(product);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertTrue(response.getBody().equals(product));
		Assert.assertEquals(response.getBody().hashCode(), product.hashCode());
		log.info("Created new product: {}.", product);
	}

	@Test
	@Ignore
	public void testIngest() throws JobExecutionAlreadyRunningException, JobRestartException,
	JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		ResponseEntity<JobExecution> jobStatus = productService.ingest();
		Assert.assertNotNull(jobStatus);
		Assert.assertEquals(jobStatus.getStatusCode(), HttpStatus.OK);
	}
}

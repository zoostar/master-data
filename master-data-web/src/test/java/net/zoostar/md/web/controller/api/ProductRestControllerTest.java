package net.zoostar.md.web.controller.api;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.zoostar.md.model.Product;

public class ProductRestControllerTest extends AbstractBaseRestControllerTest {

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
	public void testIngest() throws JobExecutionAlreadyRunningException, JobRestartException,
	JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		ResponseEntity<JobExecution> jobStatus = productService.ingest();
		Assert.assertNotNull(jobStatus);
		Assert.assertEquals(jobStatus.getStatusCode(), HttpStatus.OK);
	}
}

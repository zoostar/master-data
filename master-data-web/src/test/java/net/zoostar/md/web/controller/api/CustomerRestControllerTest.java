package net.zoostar.md.web.controller.api;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import net.zoostar.md.model.Customer;

public class CustomerRestControllerTest extends AbstractBaseRestControllerTest {

	protected CustomerRestController customerService;
	
	@Autowired
	public void setCustomerService(CustomerRestController customerService) {
		this.customerService = customerService;
	}

	@Test
	public void testIngest() throws JobExecutionAlreadyRunningException, JobRestartException,
	JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		ResponseEntity<JobExecution> response = customerService.ingest();
		Assert.assertNotNull(response);
		Assert.assertEquals(BatchStatus.COMPLETED, response.getBody().getStatus());
	}
	
	@Test
	@Transactional
	public void testCreate() {
		Assert.assertNotNull(customerService);
		Customer customer = new Customer();
		customer.setEmail("john@doe.com");
		customer.setName("John Doe");
		ResponseEntity<Customer> response = customerService.create(customer);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertTrue(response.getBody().equals(customer));
		Assert.assertEquals(response.getBody().hashCode(), customer.hashCode());
		log.info("Created new customer: {}.", customer);
	}

}

package net.zoostar.md.web.controller.api;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.md.model.Customer;
import net.zoostar.md.rule.impl.RequiredFieldException;
import net.zoostar.md.service.CustomerService;

@Slf4j
@RestController
@ToString
@RequestMapping("/api/customer")
public class CustomerRestController {

	protected CustomerService customerManager;
	
	protected JobLauncher jobLauncher;
	
	protected Job jobIngestCustomer;
	
	@Autowired
	public void setCustomerManager(CustomerService customerManager) {
		log.debug("setCustomerManager({})", customerManager);
		this.customerManager = customerManager;
	}
	
	@Autowired
	public void setJobLauncher(JobLauncher jobLauncher) {
		log.debug("setJobLauncher({})", jobLauncher);
		this.jobLauncher = jobLauncher;
	}
	
	@Autowired
	public void setJobIngestCustomer(Job jobIngestCustomer) {
		log.debug("setJobIngestCustomer({})", jobIngestCustomer);
		this.jobIngestCustomer = jobIngestCustomer;
	}
	
	@GetMapping(path = "/ingest")
	public ResponseEntity<BatchStatus> triggerIngestionJob() throws JobExecutionAlreadyRunningException,
	JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobExecution jobExecution = jobLauncher.run(jobIngestCustomer, new JobParameters());
		do {
			log.info("{}", "Job execution in progress...");
		} while(jobExecution.isRunning());
		
		log.info("{}", "Job execution complete.");
		return new ResponseEntity<>(jobExecution.getStatus(), HttpStatus.OK);
	}
	
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> create(@RequestBody Customer customer) {
		log.info("create({})", customer);
		ResponseEntity<Customer> response;
		try {
			response = new ResponseEntity<>(customerManager.create(customer), HttpStatus.OK);
		} catch(RequiredFieldException e) {
			response = new ResponseEntity<>(customer, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}

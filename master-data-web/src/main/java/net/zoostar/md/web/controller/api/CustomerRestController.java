package net.zoostar.md.web.controller.api;

import java.util.List;
import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.ToString;
import net.zoostar.md.exception.RecordNotFoundException;
import net.zoostar.md.model.Customer;
import net.zoostar.md.service.CustomerService;
import net.zoostar.md.service.GenericService;

@ToString
@RestController
@RequestMapping("/api/customer")
public class CustomerRestController extends AbstractGenericRestController<Customer, UUID> {

	protected Job jobIngestCustomer;
	
	private CustomerService genericManager;
	
	@Autowired
	public void setGenericManager(CustomerService genericManager) {
		this.genericManager = genericManager;
	}
	
	@Autowired
	public void setJobIngestCustomer(Job jobIngestCustomer) {
		log.debug("setJobIngestCustomer({})", jobIngestCustomer);
		this.jobIngestCustomer = jobIngestCustomer;
	}
	
	@GetMapping(path = "/ingest")
	public ResponseEntity<JobExecution> ingest() throws JobExecutionAlreadyRunningException,
	JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		return triggerIngestionJob(jobIngestCustomer, new JobParameters());
	}
	
	@Override
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> create(@RequestBody Customer customer) {
		return super.create(customer);
	}
	
	@GetMapping(path = "/retrieve/email")
	public ResponseEntity<Customer> retrieveByEmail(@RequestParam(name = "email", required = true) String email) {
		ResponseEntity<Customer> response = null;
		log.info("Retrieve by email: {}", email);
		try {
			response = new ResponseEntity<>(genericManager.retrieveByEmail(email), HttpStatus.OK);
		} catch(RecordNotFoundException e) {
			log.warn(e.getMessage(), e);
			HttpHeaders headers = new HttpHeaders();
			headers.set("error", e.getMessage());
			response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping(path = "/retrieve/name", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Customer>> retrieveByName(@RequestParam(name = "name", required = true) String name) {
		log.info("Retrieve by last name: {}", name);
		List<Customer> customers = getCustomerManager().retrieveByName(name);
		log.info("Found {} record(s) for name: {}", customers.size(), name);
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}
	
	@PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> update(@RequestBody Customer customer) {
		return super.update(customer);
	}
	
	@GetMapping(path = "/delete")
	public ResponseEntity<Customer> delete(@RequestParam(name = "email", required = true) String email) {
		return new ResponseEntity<>(getCustomerManager().deleteByEmail(email), HttpStatus.OK);
	}

	@Override
	public GenericService<Customer, UUID> getGenericManager() {
		return getCustomerManager();
	}
	
	public CustomerService getCustomerManager() {
		return genericManager;
	}
}

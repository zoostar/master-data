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
import org.springframework.dao.DataIntegrityViolationException;
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
import lombok.extern.slf4j.Slf4j;
import net.zoostar.md.exception.RecordNotFoundException;
import net.zoostar.md.model.Customer;
import net.zoostar.md.rule.exception.RequiredFieldException;
import net.zoostar.md.service.CustomerService;

@Slf4j
@RestController
@ToString
@RequestMapping("/api/customer")
public class CustomerRestController extends AbstractGenericRestController<Customer, UUID> {

	protected CustomerService customerManager;
	
	protected Job jobIngestCustomer;
	
	@Autowired
	public void setCustomerManager(CustomerService customerManager) {
		log.debug("setCustomerManager({})", customerManager);
		this.customerManager = customerManager;
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
	
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> create(@RequestBody Customer customer) {
		log.info("create({})...", customer);
		ResponseEntity<Customer> response;
		try {
			response = new ResponseEntity<>(customerManager.create(customer), HttpStatus.OK);
		} catch(RequiredFieldException | DataIntegrityViolationException e) {
			log.warn(e.getMessage(), e);
			HttpHeaders headers = new HttpHeaders();
			headers.set("error", e.getMessage());
			response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping(path = "/retrieve/email")
	public ResponseEntity<Customer> retrieveByEmail(@RequestParam(name = "email", required = true) String email) {
		ResponseEntity<Customer> response = null;
		log.info("Retrieve by email: {}", email);
		try {
			response = new ResponseEntity<>(customerManager.retrieveByEmail(email), HttpStatus.OK);
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
		List<Customer> customers = customerManager.retrieveByName(name);
		log.info("Found {} record(s) for name: {}", customers.size(), name);
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}
	
	@PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> update(@RequestBody Customer customer) {
		log.info("update({})...", customer);
		ResponseEntity<Customer> response;
		try {
			response = new ResponseEntity<>(customerManager.update(customer), HttpStatus.OK);
		} catch(RecordNotFoundException | RequiredFieldException e) {
			log.warn(e.getMessage(), e);
			HttpHeaders headers = new HttpHeaders();
			headers.set("error", e.getMessage());
			response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@GetMapping(path = "/delete")
	public ResponseEntity<Customer> delete(@RequestParam(name = "email", required = true) String email) {
		log.info("delete({})...", email);
		ResponseEntity<Customer> response = null;
		try {
			response = new ResponseEntity<>(customerManager.delete(email), HttpStatus.OK);
		} catch(RecordNotFoundException e) {
			log.warn(e.getMessage());
			HttpHeaders headers = new HttpHeaders();
			headers.set("error", e.getMessage());
			response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}

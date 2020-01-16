package net.zoostar.md.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.md.model.Customer;
import net.zoostar.md.service.CustomerService;

@Slf4j
@RestController
@ToString
@RequestMapping("/api/customer")
public class CustomerRestController {

	protected CustomerService customerManager;
	
	@Autowired
	public void setCustomerManager(CustomerService customerManager) {
		log.debug("setCustomerManager({})", customerManager);
		this.customerManager = customerManager;
	}
	
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> create(@RequestBody Customer customer) {
		log.info("create({})", customer);
		return new ResponseEntity<>(customerManager.create(customer), HttpStatus.OK);
	}
}

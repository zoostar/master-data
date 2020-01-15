package net.zoostar.md.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.zoostar.md.model.Customer;
import net.zoostar.md.service.CustomerService;

@Slf4j
@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {

	protected CustomerService customerManager;
	
	@Autowired
	public void setCustomerManager(CustomerService customerManager) {
		log.debug("setCustomerManager({})", customerManager);
		this.customerManager = customerManager;
	}
	
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer create(Customer customer) {
		log.info("create({})", customer);
		return customerManager.create(customer);
	}
}

package net.zoostar.md.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.md.dao.CustomerRepository;
import net.zoostar.md.exception.RecordNotFoundException;
import net.zoostar.md.model.Customer;
import net.zoostar.md.rule.impl.CustomerRequiredFieldRule;
import net.zoostar.md.service.CustomerService;

@Slf4j
@ToString
@Service
public class CustomerServiceImpl implements CustomerService {

	protected CustomerRepository customerRepository;

	protected CustomerRequiredFieldRule customerRequiredFieldRule;
	
	@Autowired
	public void setCustomerRequiredFieldRule(CustomerRequiredFieldRule customerRequiredFieldRule) {
		log.debug("setCustomerRequiredFieldRule({})", customerRequiredFieldRule);
		this.customerRequiredFieldRule = customerRequiredFieldRule;
	}
	
	@Autowired
	public void setCustomerRepository(CustomerRepository customerRepository) {
		log.debug("setCustomerRepository({})", customerRepository);
		this.customerRepository = customerRepository;
	}
	
	@Override
	public Customer create(Customer customer) {
		log.info("create({})", customer);
		customerRequiredFieldRule.apply(customer);
		return customerRepository.save(customer);
	}

	@Override
	public Customer retrieveByEmail(String email) {
		log.info("retrieveByEmail({})", email);
		Customer customer = customerRepository.findByEmail(email);
		if(customer == null) {
			throw new RecordNotFoundException("Record not found for email: " + email);
		}
		return customer;
	}

	@Override
	public List<Customer> retrieveByName(String name) {
		log.info("retrieveByName({})", name);
		return customerRepository.findByName(name);
	}

}

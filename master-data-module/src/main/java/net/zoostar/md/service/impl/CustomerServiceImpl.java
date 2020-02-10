package net.zoostar.md.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(readOnly = true)
public class CustomerServiceImpl extends AbstractGenericService<Customer, UUID> implements CustomerService {

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
	protected CrudRepository<Customer, UUID> getGenericCrudRepository() {
		return customerRepository;
	}
	
	protected void validate(Customer customer) {
		customerRequiredFieldRule.apply(customer);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Customer create(Customer customer) {
		log.info("Creating a new customer: {}...", customer);
		validate(customer);
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

	@Override
	@Transactional(readOnly = false)
	public Customer update(Customer customer) {
		log.info("Updating customer: {}...", customer);
		Customer entity = retrieveByEmail(customer.getEmail());
		entity.setName(customer.getName());
		validate(entity);
		return customerRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public Customer delete(String email) {
		log.info("Deleting customer by email id: {}...", email);
		Customer entity = retrieveByEmail(email);
		customerRepository.delete(entity);
		return entity;
	}
}

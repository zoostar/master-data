package net.zoostar.md.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.zoostar.md.dao.CustomerRepository;
import net.zoostar.md.model.Customer;
import net.zoostar.md.service.CustomerService;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

	protected CustomerRepository customerRepository;
	
	@Autowired
	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	@Override
	public Customer create(Customer customer) {
		log.info("create({})", customer);
		return customerRepository.save(customer);
	}

}

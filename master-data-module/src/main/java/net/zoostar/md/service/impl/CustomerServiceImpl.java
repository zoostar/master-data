package net.zoostar.md.service.impl;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.zoostar.md.model.Customer;
import net.zoostar.md.service.CustomerService;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

	@Override
	public Customer create(Customer customer) {
		log.info("create({})", customer);
		// TODO Auto-generated method stub
		return null;
	}

}

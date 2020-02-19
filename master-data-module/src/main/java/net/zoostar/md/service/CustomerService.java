package net.zoostar.md.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.zoostar.md.model.Customer;

public interface CustomerService extends GenericService<Customer, UUID> {
	Customer retrieveByEmail(String email);
	List<Customer> retrieveByName(String lname);
	Customer deleteByEmail(String email);
	Page<Customer> retrieveAll(Pageable page);
}

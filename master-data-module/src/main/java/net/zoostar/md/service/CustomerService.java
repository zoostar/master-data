package net.zoostar.md.service;

import java.util.List;
import java.util.UUID;

import net.zoostar.md.model.Customer;

public interface CustomerService extends GenericService<Customer, UUID> {
	Customer retrieveByEmail(String email);
	List<Customer> retrieveByName(String lname);
	Customer deleteByEmail(String email);
}

package net.zoostar.md.service;

import java.util.List;

import net.zoostar.md.model.Customer;

public interface CustomerService {
	Customer create(Customer customer);
	Customer retrieveByEmail(String email);
	List<Customer> retrieveByName(String lname);
}

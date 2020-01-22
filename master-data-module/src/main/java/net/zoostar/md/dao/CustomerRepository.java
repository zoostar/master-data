package net.zoostar.md.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import net.zoostar.md.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {
	Customer findByEmail(String email);
	List<Customer> findByName(String name);
}

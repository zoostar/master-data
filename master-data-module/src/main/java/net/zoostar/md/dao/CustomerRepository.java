package net.zoostar.md.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import net.zoostar.md.model.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, UUID> {
	Customer findByEmail(String email);
	List<Customer> findByName(String name);
}

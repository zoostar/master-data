package net.zoostar.md.job.processor;

import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;

import net.zoostar.md.model.Customer;

public class CustomerItemProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer customer) throws Exception {
		if(customer.isNew()) {
			customer.setId(UUID.randomUUID());
		}
		return customer;
	}

}

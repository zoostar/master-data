package net.zoostar.md.job.processor;

import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import net.zoostar.md.model.Customer;
import net.zoostar.md.rule.impl.CustomerRequiredFieldRule;

@Slf4j
public class CustomerItemProcessor implements ItemProcessor<Customer, Customer> {

	protected CustomerRequiredFieldRule customerRequiredFieldRule;
	
	@Autowired
	public void setCustomerRequiredFieldRule(CustomerRequiredFieldRule customerRequiredFieldRule) {
		log.debug("setCustomerRequiredFieldRule({})", customerRequiredFieldRule);
		this.customerRequiredFieldRule = customerRequiredFieldRule;
	}
	
	@Override
	public Customer process(Customer customer) throws Exception {
		log.info("Processing item: {}", customer);
		customerRequiredFieldRule.apply(customer);
		
		if(customer.isNew()) {
			customer.setId(UUID.randomUUID());
		}
		log.info("{}", "Processing item complete.");
		return customer;
	}

}

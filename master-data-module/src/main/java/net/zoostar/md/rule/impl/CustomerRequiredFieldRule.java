package net.zoostar.md.rule.impl;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.zoostar.md.model.Customer;
import net.zoostar.md.rule.BusinessRule;
import net.zoostar.md.rule.exception.BusinessRuleException;
import net.zoostar.md.rule.exception.RequiredFieldException;

@Slf4j
@Component
public class CustomerRequiredFieldRule implements BusinessRule<Customer, UUID> {

	@Override
	public void apply(Customer customer) throws BusinessRuleException {
		if(StringUtils.isBlank(customer.getEmail())) {
			log.warn("Required field customer.email is null/empty!");
			throw new RequiredFieldException("Field [customer.email] cannot be null or empty!", customer);
		}
	}

}

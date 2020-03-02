package net.zoostar.md.web.controller.api;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import net.zoostar.md.model.Customer;
import net.zoostar.md.model.Timesheet;
import net.zoostar.md.model.TimesheetState;

public class TimesheetRestControllerTest extends AbstractBaseRestControllerTest {
	
	@Test
	@Transactional
	public void testSubmit() {
		Customer customer = createCustomer();
		Timesheet timesheet = timesheetService.generateNewTimesheet(customer).getBody();
		timesheet.setCustomer(customer);
		Assert.assertEquals(TimesheetState.NEW.toString(), timesheet.getPersistentState());
		timesheetService.submit(timesheet);
		Assert.assertEquals(TimesheetState.SUBMITTED.toString(), timesheet.getPersistentState());
	}

	private Customer createCustomer() {
		Customer customer = new Customer();
		customer.setEmail("john@doe.com");
		customer.setName("John Doe");
		return customerService.create(customer).getBody();
	}
}

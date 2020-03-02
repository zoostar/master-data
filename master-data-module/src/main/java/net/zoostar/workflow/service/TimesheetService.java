package net.zoostar.workflow.service;

import java.util.UUID;

import net.zoostar.md.model.Customer;
import net.zoostar.md.model.Timesheet;
import net.zoostar.md.service.GenericService;

public interface TimesheetService extends GenericService<Timesheet, UUID> {
	Timesheet generateNew(Customer customer);
	void submit(Timesheet timesheet);
	void approve(Timesheet timesheet);
}

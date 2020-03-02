package net.zoostar.workflow.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.zoostar.md.model.Customer;
import net.zoostar.md.model.Timesheet;
import net.zoostar.md.model.TimesheetAction;
import net.zoostar.workflow.Action;
import net.zoostar.workflow.State;
import net.zoostar.workflow.dao.TimesheetRepository;
import net.zoostar.workflow.service.TimesheetService;

@Slf4j
@Service
@Transactional
public class TimesheetServiceImpl implements TimesheetService {

	protected TimesheetRepository timesheetRepository;
	
	protected State<Timesheet> timesheetStateNew;
	
	@Autowired
	public void setTimesheetRepository(TimesheetRepository timesheetRepository) {
		log.debug("setTimesheetRepository({})", timesheetRepository);
		this.timesheetRepository = timesheetRepository;
	}
	
	@Autowired
	public void setTimesheetStateNew(State<Timesheet> timesheetStateNew) {
		this.timesheetStateNew = timesheetStateNew;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Timesheet create(Timesheet timesheet) {
		return timesheetRepository.save(timesheet);
	}

	@Override
	public Timesheet update(Timesheet timesheet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(UUID id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Timesheet generateNew(Customer customer) {
		Timesheet timesheet = new Timesheet(timesheetStateNew);
		timesheet.setCustomer(customer);
		return timesheet;
	}

	@Override
	@Transactional(readOnly = false)
	public void submit(Timesheet timesheet) {
		State<Timesheet> currentState = timesheet.getCurrentState();
		for(Action<Timesheet> action : currentState.getActions()) {
			if(TimesheetAction.SUBMIT.toString().equalsIgnoreCase(action.toString())) {
				action.execute(timesheet);
				break;
			}
		}
	}

	@Override
	public void approve(Timesheet timesheet) {
		// TODO Auto-generated method stub
		
	}

}

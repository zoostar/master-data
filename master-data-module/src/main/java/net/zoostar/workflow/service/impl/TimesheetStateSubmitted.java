package net.zoostar.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.md.model.Timesheet;
import net.zoostar.md.model.TimesheetAction;
import net.zoostar.md.model.TimesheetState;
import net.zoostar.workflow.Action;
import net.zoostar.workflow.State;
import net.zoostar.workflow.service.TimesheetService;

@Slf4j
@Getter
@Service
public class TimesheetStateSubmitted implements State<Timesheet>, InitializingBean {

	private List<Action<Timesheet>> actions = new ArrayList<>(2);
	
	private TimesheetService timesheetManager;
	
	@Autowired
	public void setTimesheetManager(TimesheetService timesheetManager) {
		log.debug("setTimesheetManager({})", timesheetManager);
		this.timesheetManager = timesheetManager;
	}

	@Override
	public String toString() {
		return TimesheetState.SUBMITTED.toString();
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		initSubmitAction();
	}

	private void initSubmitAction() {
		actions.add(new Action<Timesheet>() {
			
			@Override
			public void execute(Timesheet timesheet) {
				timesheet.setCurrentState(new State<Timesheet>() {

					@Override
					public List<Action<Timesheet>> getActions() {
						// TODO Auto-generated method stub
						return null;
					}
					
				});
				timesheet.setPersistentState(TimesheetState.APPROVED.toString());
				timesheetManager.approve(timesheet);
			}

			@Override
			public String toString() {
				return TimesheetAction.SUBMIT.toString();
			}
			
		});
		
	}
}

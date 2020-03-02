package net.zoostar.md.web.controller.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.ToString;
import net.zoostar.md.model.Customer;
import net.zoostar.md.model.Timesheet;
import net.zoostar.md.service.GenericService;
import net.zoostar.workflow.service.TimesheetService;

@ToString
@RestController
@RequestMapping("/api/timesheet")
public class TimesheetRestController extends AbstractGenericRestController<Timesheet, UUID> {

	private TimesheetService genericManager;
	
	@Autowired
	public void setGenericManager(TimesheetService genericManager) {
		this.genericManager = genericManager;
	}

	@Override
	public GenericService<Timesheet, UUID> getGenericManager() {
		return getTimesheetManager();
	}
	
	protected TimesheetService getTimesheetManager() {
		return this.genericManager;
	}

	@PostMapping(path = "/submit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Timesheet> submit(@RequestBody Timesheet timesheet) {
		genericManager.submit(timesheet);
		return new ResponseEntity<>(timesheet, HttpStatus.OK);
	}

	@PostMapping(path = "/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Timesheet> generateNewTimesheet(@RequestBody Customer customer) {
		return new ResponseEntity<>(genericManager.generateNew(customer), HttpStatus.OK);
	}

}

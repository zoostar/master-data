package net.zoostar.workflow.dao;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import net.zoostar.md.model.Timesheet;

public interface TimesheetRepository extends CrudRepository<Timesheet, UUID> {

}

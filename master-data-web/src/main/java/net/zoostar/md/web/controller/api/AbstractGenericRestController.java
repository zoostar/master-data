package net.zoostar.md.web.controller.api;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Persistable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.zoostar.md.rule.impl.RequiredFieldException;
import net.zoostar.md.service.GenericService;

public abstract class AbstractGenericRestController<T extends Persistable<ID>, ID extends Serializable> {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	protected GenericService<T, ID> genericManager;
	
	protected JobLauncher jobLauncher;
	
	@Autowired
	public void setGenericManager(GenericService<T, ID> genericManager) {
		log.debug("setGenericManager({})", genericManager);
		this.genericManager = genericManager;
	}
	
	@Autowired
	public void setJobLauncher(JobLauncher jobLauncher) {
		log.debug("setJobLauncher({})", jobLauncher);
		this.jobLauncher = jobLauncher;
	}
	
	protected ResponseEntity<JobExecution> triggerIngestionJob(Job job, JobParameters jobParams) throws JobExecutionAlreadyRunningException,
	JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobExecution jobExecution = jobLauncher.run(job, jobParams);
		do {
			log.info("{}", "Job execution in progress...");
		} while(jobExecution.isRunning());
		
		log.info("{}", "Job execution complete.");
		BatchStatus batchStatus = jobExecution.getStatus();
		if(batchStatus.isUnsuccessful()) {
			return new ResponseEntity<>(jobExecution, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(jobExecution, HttpStatus.OK);
	}
	
	public ResponseEntity<T> create(T persistable) {
		log.info("create({})...", persistable);
		ResponseEntity<T> response;
		try {
			response = new ResponseEntity<>(genericManager.create(persistable), HttpStatus.OK);
		} catch(RequiredFieldException | DataIntegrityViolationException e) {
			log.warn(e.getMessage(), e);
			HttpHeaders headers = new HttpHeaders();
			headers.set("error", e.getMessage());
			response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}

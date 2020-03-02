package net.zoostar.md.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.zoostar.workflow.State;
import net.zoostar.workflow.Workflowable;

@Getter
@Setter
@Entity
@ToString
public class Timesheet implements Workflowable<Timesheet>, Persistable<UUID> {

	private UUID id;
	
	private Customer customer;
	
	private int hours;
	
	private Date periodEnding;
	
	private String persistentState;
	
	private transient State<Timesheet> currentState;
	
	public Timesheet(State<Timesheet> initialState) {
		if(initialState == null)
			throw new NullPointerException("Initial state cannot be NULL!");
		
		this.currentState = initialState;
		this.persistentState = currentState.toString();
	}
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid2")
	@JsonIgnore
	@Override
	public UUID getId() {
		return this.id;
	}

	@Transient
	@JsonIgnore
	@Override
	public boolean isNew() {
		return this.id == null;
	}
	
	@Transient
	@Override
	public State<Timesheet> getCurrentState() {
		return this.currentState;
	}
	
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}
}

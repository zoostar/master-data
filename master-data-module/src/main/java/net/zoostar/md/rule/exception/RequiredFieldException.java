package net.zoostar.md.rule.exception;

import java.io.Serializable;

import org.springframework.data.domain.Persistable;

public class RequiredFieldException extends BusinessRuleException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequiredFieldException(String message, Persistable<? extends Serializable> persistable) {
		super(message, persistable);
	}

}

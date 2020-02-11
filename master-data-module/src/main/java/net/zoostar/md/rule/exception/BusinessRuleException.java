package net.zoostar.md.rule.exception;

import java.io.Serializable;

import org.springframework.data.domain.Persistable;

public abstract class BusinessRuleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Persistable<? extends Serializable> persistable;
	
	public BusinessRuleException(Persistable<? extends Serializable> persistable) {
		super();
		this.persistable = persistable;
	}

	public BusinessRuleException(String message, Persistable<? extends Serializable> persistable) {
		super(message);
		this.persistable = persistable;
	}

	public BusinessRuleException(Throwable cause, Persistable<? extends Serializable> persistable) {
		super(cause);
		this.persistable = persistable;
	}

	public BusinessRuleException(String message, Throwable cause, Persistable<? extends Serializable> persistable) {
		super(message, cause);
		this.persistable = persistable;
	}

	public BusinessRuleException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace, Persistable<? extends Serializable> persistable) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.persistable = persistable;
	}

	public Persistable<? extends Serializable> getPersistable() {
		return this.persistable;
	}
}

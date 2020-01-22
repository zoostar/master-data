package net.zoostar.md.exception;

import java.io.Serializable;

import org.springframework.data.domain.Persistable;

public abstract class AbstractMasterDataRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	protected Persistable<? extends Serializable> persistable;
	
	public AbstractMasterDataRuntimeException(String message, Persistable<? extends Serializable> persistable) {
		super(message);
		this.persistable = persistable;
	}

}

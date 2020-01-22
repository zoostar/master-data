package net.zoostar.md.exception;

import java.io.Serializable;

import org.springframework.data.domain.Persistable;

public class DuplicateRecordException extends AbstractMasterDataRuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateRecordException(String message, Persistable<? extends Serializable> persistable) {
		super(message, persistable);
	}

}

package net.zoostar.md.exception;

public abstract class AbstractMasterDataRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AbstractMasterDataRuntimeException(String message) {
		super(message);
	}

}

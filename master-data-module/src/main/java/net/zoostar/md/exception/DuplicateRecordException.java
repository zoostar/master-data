package net.zoostar.md.exception;

public class DuplicateRecordException extends AbstractMasterDataRuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateRecordException(String message) {
		super(message);
	}

}

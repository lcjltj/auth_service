package spring.cloud.auth.exception;

import lombok.Getter;

@Getter
public class DefaultException extends RuntimeException {

	private static final long serialVersionUID = -8221495409365185745L;
	private final int errorCode;

	public DefaultException(DefaultExceptionType defaultExceptionType) {
		super(defaultExceptionType.getMessage());
		this.errorCode = defaultExceptionType.getErrorCode();
	}
	
	public DefaultException(JwtExceptionType jwtExceptionType) {
		super(jwtExceptionType.getMessage());
		this.errorCode = jwtExceptionType.getErrorCode();
	}
}
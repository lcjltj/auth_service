package spring.cloud.auth.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

	private static final long serialVersionUID = 3835863001045179661L;
	private final int errorCode;

	public UserException(UserExceptionType userExceptionType) {
		super(userExceptionType.getMessage());
		this.errorCode = userExceptionType.getErrorCode();
	}
}
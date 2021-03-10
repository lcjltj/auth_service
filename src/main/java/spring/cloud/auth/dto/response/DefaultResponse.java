package spring.cloud.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import spring.cloud.auth.exception.UserExceptionType;

@Getter
@Setter
@Builder
@ToString
public class DefaultResponse<T> {
	
	private final String message;
	private final int errorCode;
	private final T body;

	public DefaultResponse (){
		this.message = null;
		this.errorCode = 0;
		this.body = null;
	}
	
	public DefaultResponse (T body) { // 성공
		this.message = null;
		this.errorCode = 0;
		this.body = body;
	}
	
	public DefaultResponse (String message, int errorCode) { // 실패
		this(message, errorCode, null);
	}
	
	public DefaultResponse (UserExceptionType userExcetpionType) { //실패
		this(userExcetpionType, null);
	}
	
	public DefaultResponse (String message, int errorCode, T body) { // 실패 
		this.message = message;
		this.errorCode = errorCode;
		this.body = body;
	}
	
	public DefaultResponse (UserExceptionType userExcetpionType, T body) { //실패
		this.message = userExcetpionType.getMessage();
		this.errorCode = userExcetpionType.getErrorCode();
		this.body = body;
	}
}

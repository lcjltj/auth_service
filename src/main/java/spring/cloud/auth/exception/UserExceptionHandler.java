package spring.cloud.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.extern.slf4j.Slf4j;
import spring.cloud.auth.dto.response.DefaultResponse;

@ControllerAdvice
@Slf4j
public class UserExceptionHandler {

	/*
	 * @Valid 유효성 체크 오류
	 */
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<DefaultResponse> validationException(MethodArgumentNotValidException e) {
		String message = e.getBindingResult().getFieldError().getDefaultMessage();
		DefaultResponse errorResult = DefaultResponse
									.builder()
									.message(message)
									.build();
		log.warn(message);
		return new ResponseEntity<DefaultResponse>(errorResult, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
	public ResponseEntity<DefaultResponse> dataException(org.springframework.dao.DataIntegrityViolationException e) {
		DefaultResponse errorResult = DefaultResponse
				.builder()
				.message(e.getMessage())
				.build();
		log.warn(e.getMessage());
		return new ResponseEntity<DefaultResponse>(errorResult, HttpStatus.OK);
	}	
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(UserException.class)
	public ResponseEntity<DefaultResponse> userException(UserException e) {
		DefaultResponse errorResult = DefaultResponse
				.builder()
				.message(e.getMessage())
				.errorCode(e.getErrorCode())
				.build();
		
		return new ResponseEntity<DefaultResponse>(errorResult, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(DefaultException.class)
	public ResponseEntity<DefaultResponse> userException(DefaultException e) {
		DefaultResponse errorResult = DefaultResponse
				.builder()
				.message(e.getMessage())
				.errorCode(e.getErrorCode())
				.build();
		
		return new ResponseEntity<DefaultResponse>(errorResult, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<DefaultResponse> userException(InvalidFormatException e) {
		String message = null;
		
		if(e.getPathReference().contains("gender")) {
			message = "성별은 M/F 입력만 가능합니다.";
		} else if (e.getPathReference().contains("role")) {
			message = "권한은  U(user)/A(admin)만 가능합니다.";
		} else if (e.getPathReference().contains("search")) {
			message = "검색 조건은  name/email 만 가능합니다.";
		}
		
		DefaultResponse errorResult = DefaultResponse
				.builder()
				.message(message)
				.build();
		
		return new ResponseEntity<DefaultResponse>(errorResult, HttpStatus.OK);
	}
}

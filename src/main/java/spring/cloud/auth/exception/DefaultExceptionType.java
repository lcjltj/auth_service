package spring.cloud.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DefaultExceptionType {
	
	NOT_LOGIN("로그인 후 이용해 주세요.",105),
	INVALID_REQUEST("잘못된 요청입니다.",106),
	NOT_ALLOW_USER("사용 권한이 없습니다.",106);
	
	private final String message;
	private final int errorCode;
}

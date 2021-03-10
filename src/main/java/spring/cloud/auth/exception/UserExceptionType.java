package spring.cloud.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionType {
	
	USED_EMAIL("사용중인 이메일 입니다.",100),
	USED_NICKANAME("사용중인 닉네임 입니다.",101),
	FAIL_SIGNUP("회원가입이 정상적으로 되지 않았습니다.",102),
	NOT_REGISTER_EMAIL("등록되지 않은 이메일 입니다.",103),
	PASSWORD_MISSMATCH("비밀번호를 잘못 입력 하셨습니다.",104),
	PAUSED_USER("일시 정지된 회원입니다.",105),
	NOT_LOGIN("로그인 후 이용해 주세요.",106),
	NO_SEARCH_USER("조회된 결과가 없습니다.",107);
	
	private final String message;
	private final int errorCode;
}

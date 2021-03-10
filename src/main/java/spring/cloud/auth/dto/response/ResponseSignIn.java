package spring.cloud.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseSignIn {
	
	private final String accessToken;
	private final String tokenType;
	private final String expiredTime;

}

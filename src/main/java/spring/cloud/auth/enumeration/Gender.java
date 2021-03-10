package spring.cloud.auth.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
	M("남성","Male"),
	F("여성","FeMale");
	
	private final String valueOfKor;
	private final String valueOfEng;
}

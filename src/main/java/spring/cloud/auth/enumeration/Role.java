package spring.cloud.auth.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	A("ADMIN"),
	U("USER");
	
	private final String valueOf;

}

package spring.cloud.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.cloud.auth.entity.User;
import spring.cloud.auth.enumeration.Gender;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDetail {

	private Long number;
	private  String name;
	private  String nickName;
	private  String phone;
	private  String email;
	private  Gender gender;
	
	public ResponseUserDetail toDto(User user) {
		return ResponseUserDetail.builder()
				.number(user.getNumber())
				.name(user.getName())
				.nickName(user.getNickName())
				.phone(user.getPhone())
				.email(user.getEmail())
				.gender(user.getGender())
				.build();
	}
}

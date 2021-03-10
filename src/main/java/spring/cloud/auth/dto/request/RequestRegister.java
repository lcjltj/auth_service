package spring.cloud.auth.dto.request;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.cloud.auth.entity.User;
import spring.cloud.auth.enumeration.Gender;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class RequestRegister {

	@NotNull(message = "이름을 입력해주세요.")
	@Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름은 한글, 영문 대소문자만 허용합니다.")
	@Length(max = 20, message = "이름은 최대 20자리 까지만 입력 가능합니다.")
	@ApiModelProperty(notes = "사용자 이름(한글, 영문 대소문자만 허용)", required = true, position = 1)
	private String name;

	@NotNull(message = "닉네임을 입력해주세요.")
	@Pattern(regexp = "^[a-z]+$", message = "닉네임은 영문 소문자만 허용합니다.")
	@Length(max = 30, message = "닉네임은 최대 30자리 까지만 입력 가능합니다.")
	@ApiModelProperty(notes = "사용자 닉네임(영문 소문자만 허용)", required = true, position = 2)
	private String nickName;

	@NotNull(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^ ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]).{10,}$", message = "비밀번호는 영어 대소문자,한글,특수문자 최소 1개이상 /10자리 이상")
	@ApiModelProperty(notes = "비밀번호(영어 대소문자,한글,특수문자 최소 1개이상 /10자리 이상)", required = true, position = 3)
	@Setter
	private String password;

	@NotNull(message = "휴대폰 번호를 입력해주세요.")
	@Pattern(regexp = "^[0-9]+$", message = "휴대폰 번호는 숫자만 입력 가능합니다.")
	@Length(max = 20, message = "휴대폰 번호는 최대 20자리 까지만 입력 가능합니다.")
	@ApiModelProperty(notes = "휴대폰 번호(숫자만 허용)", required = true, position = 4)
	private String phone;

	@NotNull(message = "이메일을 입력해주세요.")
	@Email
	@Length(max = 100, message = "이메일은 최대 100자리 까지만 입력 가능합니다.")
	@ApiModelProperty(notes = "이메일", required = true, position = 5)
	private String email;

	@ApiModelProperty(notes = "성별(M/F)", required = true, position = 6)
	private Gender gender;

	public User toEntity() {
		return User.builder()
				.name(name)
				.nickName(nickName)
				.password(password)
				.phone(phone)
				.email(email)
				.gender(gender)
				.build();
	}
}

package spring.cloud.auth.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.cloud.auth.entity.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class RequestSignIn {
	
	@NotNull(message = "이메일을 입력해주세요.")
	@Email
	@Length(max = 100, message = "최대 100자리 까지만 입력 가능합니다.")
	@ApiModelProperty(notes = "이메일", required = true, position = 1)
	private  String email;
	
	@NotNull(message = "비밀번호를 입력해주세요.")
	@ApiModelProperty(notes = "비밀번호", required = true, position = 2)
	private  String password;
	
	public User toEntity() {
		return User.builder()
				.email(email)
				.password(password)
				.build();
	}
}

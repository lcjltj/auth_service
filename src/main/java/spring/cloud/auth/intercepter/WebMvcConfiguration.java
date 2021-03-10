package spring.cloud.auth.intercepter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

	private final LoginIntercepter loginIntercepter;
	private final InnerIntercepter innerIntercepter;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginIntercepter)
				.addPathPatterns("/user/auth/**","/user/admin/**")
				.excludePathPatterns("/user/inner/**");

		registry.addInterceptor(innerIntercepter)
				.addPathPatterns("/user/inner/**");
	}
}

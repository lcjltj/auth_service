package spring.cloud.auth.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import spring.cloud.auth.exception.DefaultException;
import spring.cloud.auth.exception.DefaultExceptionType;
import spring.cloud.auth.exception.JwtExceptionType;
import spring.cloud.auth.util.JWTUtil;
import spring.cloud.auth.util.RedisUtil;

@Component
@RequiredArgsConstructor
public class LoginIntercepter implements HandlerInterceptor {

	private final JWTUtil jwtUtil;
	private final RedisUtil redisUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String userNumber = null;
		String token = null;
		
		try {
			token = request.getHeader("AUTHORIZATION");
			String accessToken = token.split(" ")[1];
			String path = request.getServletPath();
		
			if (path.contains("/admin/") && !"A".equals(jwtUtil.getAllClaims(accessToken).get("role"))) { // 관리자 페이지 Role 관리자가 아니면 error
				throw new DefaultException(DefaultExceptionType.NOT_ALLOW_USER);
			}
			userNumber = jwtUtil.getUserNumber(accessToken);
			
		} catch (ExpiredJwtException e) {
			throw new DefaultException(JwtExceptionType.ExpiredJwtException);
		} catch (JwtException e) {
			throw new DefaultException(JwtExceptionType.JwtException);
		} catch (NullPointerException e) {
			throw new DefaultException(JwtExceptionType.NullPointerException);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new DefaultException(JwtExceptionType.JwtException);
		}
		
		redisUtil.<String>getRedisForGeneric(userNumber);

		request.setAttribute("token", token);
		request.setAttribute("userNumber", Long.parseLong(userNumber));

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}

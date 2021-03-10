package spring.cloud.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.cloud.auth.dto.request.RequestRegister;
import spring.cloud.auth.dto.request.RequestSearch;
import spring.cloud.auth.dto.request.RequestSignIn;
import spring.cloud.auth.dto.response.DefaultResponse;
import spring.cloud.auth.dto.response.ResponseLastOrder;
import spring.cloud.auth.dto.response.ResponseLastOrderUsers;
import spring.cloud.auth.dto.response.ResponseSignIn;
import spring.cloud.auth.dto.response.ResponseUserDetail;
import spring.cloud.auth.entity.Pagination;
import spring.cloud.auth.entity.User;
import spring.cloud.auth.exception.UserException;
import spring.cloud.auth.exception.UserExceptionType;
import spring.cloud.auth.mapper.UserMapper;
import spring.cloud.auth.util.JWTUtil;
import spring.cloud.auth.util.PaginationUtil;
import spring.cloud.auth.util.RedisUtil;
import spring.cloud.auth.util.RestApiUtil;

@Service
@RequiredArgsConstructor
public class UserService {

	@Value("${jwt.secret.accesstoken_timeout}")
	String accesstokenTimeout;

	@Value("${inner.lastOrder}")
	String lastOrderUrl;
	
	private final UserMapper userMapper;
	private final JWTUtil jwtUtil;
	private final RedisUtil redisUtil;
	@SuppressWarnings("rawtypes")
	private final RestApiUtil<DefaultResponse> restApi;
	private final PaginationUtil pageUtil;

	public DefaultResponse<String> registerUser(RequestRegister requestRegister) {
		findByEmailOrNickName(requestRegister.toEntity());

		SCryptPasswordEncoder encode = new SCryptPasswordEncoder();
		String encodedPassword = encode.encode(requestRegister.getPassword());
		requestRegister.setPassword(encodedPassword);
		int result = userMapper.registerUser(requestRegister.toEntity());
		
		if(result != 1) {
			throw new UserException(UserExceptionType.FAIL_SIGNUP);
		}

		return new DefaultResponse<String>("OK");
	}

	public DefaultResponse<ResponseSignIn> signIn(RequestSignIn requestSignin) {
		SCryptPasswordEncoder encode = new SCryptPasswordEncoder();
		User user = userMapper.findByEmail(requestSignin.getEmail());

		if (user == null) {
			throw new UserException(UserExceptionType.NOT_REGISTER_EMAIL);
		} else if (!encode.matches(requestSignin.getPassword(), user.getPassword())) {
			throw new UserException(UserExceptionType.PASSWORD_MISSMATCH);
		} else if ("N".equals(user.getUseYn())) {
			throw new UserException(UserExceptionType.PAUSED_USER);
		}

		String token = jwtUtil.generateToken(user);

		ResponseSignIn result = ResponseSignIn.builder()
				.accessToken(token)
				.tokenType("Bearer")
				.expiredTime(accesstokenTimeout)
				.build();
		
		redisUtil.<String>saveRedisForGeneric(Long.toString(user.getNumber()), user.getName(),Integer.parseInt(accesstokenTimeout));
		return new DefaultResponse<ResponseSignIn>(result);
	}

	public DefaultResponse<ResponseUserDetail> getDetailUser(long userNumber) {
		User user = userMapper.getUserDetail(userNumber);
		ResponseUserDetail responseUserDetail = new ResponseUserDetail().toDto(user);

		return new DefaultResponse<ResponseUserDetail>(responseUserDetail);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DefaultResponse<ResponseLastOrderUsers> getUserlist(int page,RequestSearch requestUser) {
		Long numberOfUser = userMapper.getNumberOfUser(requestUser);
	
		if(numberOfUser == 0) {
			throw new UserException(UserExceptionType.NO_SEARCH_USER);
		}
		
		Pagination pagination = pageUtil.Paging(10, page, numberOfUser);
		List<User> user =  userMapper.getUserList(requestUser, pagination);
		List<ResponseUserDetail> detailUser = new ArrayList<>();
		
		for(User listUser : user) {
			detailUser.add(new ResponseUserDetail().toDto(listUser));
		}
		
		HttpHeaders httpHeader = new HttpHeaders();
		ResponseEntity<DefaultResponse> lastOrder =  restApi.post(lastOrderUrl,httpHeader,getUserNumbers(user),DefaultResponse.class);
		ResponseLastOrderUsers list = null;
		
		if (lastOrder.getStatusCode() == HttpStatus.OK) {
			list = ResponseLastOrderUsers.builder()
					.order((List<ResponseLastOrder>) lastOrder.getBody().getBody())
					.user(detailUser)
					.pagination(pagination)
					.build();
		} else {
			list = ResponseLastOrderUsers.builder()
					.order(null)
					.user(detailUser)
					.pagination(pagination)
					.build();
		}
		return new DefaultResponse<ResponseLastOrderUsers>(list);
	}

	public DefaultResponse<String> logout(long userNumber) {
		redisUtil.deleteKey(Long.toString(userNumber)); // session 삭제
		return new DefaultResponse<String>("OK");
	}

	private void findByEmailOrNickName(User user) {
		User userFromDB = userMapper.findByEmailOrNickName(user);

		if (userFromDB != null && user.getEmail().equals(userFromDB.getEmail())) {
			throw new UserException(UserExceptionType.USED_EMAIL); // 사용중인 이메일
		} else if (userFromDB != null && user.getNickName().equals(userFromDB.getNickName())) {
			throw new UserException(UserExceptionType.USED_NICKANAME); // 사용중인 닉네임
		}
	}
	
	private List<Long> getUserNumbers(List<User> userList) {
		List<Long> userNumberList = new ArrayList<>();
		for (User user : userList) {
			userNumberList.add(user.getNumber());
		}
		return userNumberList;
	}
}

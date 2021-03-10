package spring.cloud.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import spring.cloud.auth.dto.request.RequestRegister;
import spring.cloud.auth.dto.request.RequestSearch;
import spring.cloud.auth.dto.request.RequestSignIn;
import spring.cloud.auth.dto.response.DefaultResponse;
import spring.cloud.auth.dto.response.ResponseLastOrderUsers;
import spring.cloud.auth.dto.response.ResponseSignIn;
import spring.cloud.auth.dto.response.ResponseUserDetail;
import spring.cloud.auth.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "AUTH", description = "사용자 인증")
public class UserController {

	private final UserService userService;
	
	@PostMapping("/signup")
	@ApiOperation(value = "회원 가입")
	public ResponseEntity<DefaultResponse<String>> registerUser(@ApiParam(name = "회원 가입", value =  "사용자 회원가입") 
																@RequestBody @Valid RequestRegister user) {
		DefaultResponse<String> result = userService.registerUser(user);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/signin")
	@ApiOperation(value = "로그인")
	public ResponseEntity<DefaultResponse<ResponseSignIn>> signIn(@ApiParam(name = "로그인", value =  "사용자 로그인") 
																  @RequestBody @Valid RequestSignIn user) {
		DefaultResponse<ResponseSignIn> result = userService.signIn(user);	
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/auth/logout")
	@ApiOperation(value = "로그아웃", authorizations = { @Authorization("AUTHORIZATION") })
	public ResponseEntity<DefaultResponse<String>> logout(HttpServletRequest request) {
		long userNumber = (Long) request.getAttribute("userNumber");
		DefaultResponse<String> result = userService.logout(userNumber);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/auth/user_detail")
	@ApiOperation(value = "유저 상세 조회", authorizations = { @Authorization("AUTHORIZATION") })
	public ResponseEntity<DefaultResponse<ResponseUserDetail>> getDetailUser(HttpServletRequest request) {
		long userNumber = (Long) request.getAttribute("userNumber");
		DefaultResponse<ResponseUserDetail> result = userService.getDetailUser(userNumber);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/admin/user_list/{pageNum}")
	@ApiOperation(value = "사용자 조회", authorizations = { @Authorization("AUTHORIZATION") })
	public ResponseEntity<DefaultResponse<ResponseLastOrderUsers>> getUserlist(@PathVariable int pageNum,RequestSearch requestUser) {
		DefaultResponse<ResponseLastOrderUsers> result = userService.getUserlist(pageNum,requestUser);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
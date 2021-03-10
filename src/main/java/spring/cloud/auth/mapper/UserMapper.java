package spring.cloud.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.cloud.auth.dto.request.RequestSearch;
import spring.cloud.auth.entity.Pagination;
import spring.cloud.auth.entity.User;

@Mapper
public interface UserMapper {
	public int registerUser(User user);
	
	public User findByEmailOrNickName(User user);
	
	public User findByEmail(String email);
	
	public User getUserDetail(long number);
	
	public long getNumberOfUser(RequestSearch user);
	
	public List<User> getUserList(@Param(value = "search") RequestSearch search,@Param(value = "pagination") Pagination pagination);
	
}

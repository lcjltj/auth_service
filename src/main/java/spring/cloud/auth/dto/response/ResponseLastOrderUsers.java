package spring.cloud.auth.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spring.cloud.auth.entity.Pagination;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ResponseLastOrderUsers {

	private List<ResponseLastOrder> order;
	private List<ResponseUserDetail> user;
	private Pagination pagination;
}

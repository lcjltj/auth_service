package spring.cloud.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Builder
@ToString
public class Pagination {
	
	private final int startRowNum;
	private final int endRowNum;
	private final int pageRowNum;
	private final int startPageNum;
	private final int endPageNum;
	private final int totalPageCount;
}

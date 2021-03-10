package spring.cloud.auth.util;

import org.springframework.stereotype.Component;

import spring.cloud.auth.entity.Pagination;

@Component
public class PaginationUtil {

	public Pagination Paging(int row, int userNumber, long totalCount) {
		
		if (userNumber == 0) {
			userNumber = 1;
		}

		final int pageRowCount = row;

		int startRowNum = (userNumber - 1) * pageRowCount;
		int endRowNum = userNumber * pageRowCount;
		int pageRowNum = row;

		final int PAGE_DISPLAY_COUNT = 5;

		int totalPageCount = (int) Math.ceil(totalCount / (double) pageRowCount);
		int startPageNum = 1 + ((userNumber - 1) / PAGE_DISPLAY_COUNT) * PAGE_DISPLAY_COUNT;
		int endPageNum = startPageNum + PAGE_DISPLAY_COUNT - 1;

		if(totalCount < endRowNum) {
			endRowNum = (int) totalCount;
		}
		
		if(pageRowCount < row) {
			startRowNum = 0;
		}
		
		if (totalPageCount < endPageNum) {
			endPageNum = totalPageCount; 
		}
		
		return Pagination.builder()
				.startRowNum(startRowNum)
				.endRowNum(endRowNum)
				.pageRowNum(pageRowNum)
				.startPageNum(startPageNum)
				.endPageNum(endPageNum)
				.totalPageCount(totalPageCount)
				.build();
	}
}

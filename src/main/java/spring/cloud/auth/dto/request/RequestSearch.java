package spring.cloud.auth.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import spring.cloud.auth.enumeration.SearchType;

@Getter
@AllArgsConstructor
@Builder
@ApiModel
public class RequestSearch {
	
	private SearchType searchType;
	private String keyword;
	
	public String getKeyword() {
		
		if (keyword != null) {  //MYSQL Full-Text-Search 예약문자 제거 
			String[] reservedWord = {"@","+","-","~","*"};
			for(String word : reservedWord) {
				this.keyword = keyword.replace(word, " ");
			}
			return keyword;
		}
		return keyword;
	}
}

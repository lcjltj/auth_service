package spring.cloud.auth.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ResponseLastOrder {
	private String number;
	private String productName;
	private Date lastOrderDate;
	private Long userNumber;

}

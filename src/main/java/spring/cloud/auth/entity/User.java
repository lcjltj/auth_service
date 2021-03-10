package spring.cloud.auth.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spring.cloud.auth.enumeration.Gender;
import spring.cloud.auth.enumeration.Role;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "idus_user")
public class User implements Serializable {

	private static final long serialVersionUID = 713008146255942587L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long number;

	private String name;

	private String nickName;

	private String password;

	private String phone;

	private String email;

	private Gender gender;

	private Date createDate;

	private Date updateDate;

	private Date deleteDate;

	private String useYn;
	
	private Role role;
}

package in.amjadIT.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class CustomerDTO {

	private Integer id;

	private String name;

	private String email;

	private String pwd;

	private String pwdUpdated;

	private Long phoneNo;
	
	private Date dateCreated;
	
	private Date lastUpdate;

}

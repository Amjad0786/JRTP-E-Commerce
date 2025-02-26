package in.amjadIT.dto;

import lombok.Data;

@Data
public class ResetPwdDTO {

	private Integer id;

	private String name;
	
	private String email;

	private String oldPwd;

	private String newPwd;

	private String confirmPwd;

}

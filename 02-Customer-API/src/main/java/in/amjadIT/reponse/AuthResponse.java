package in.amjadIT.reponse;

import in.amjadIT.dto.CustomerDTO;
import lombok.Data;

@Data
public class AuthResponse {

	private CustomerDTO customerDto;
	
	private String token;
}

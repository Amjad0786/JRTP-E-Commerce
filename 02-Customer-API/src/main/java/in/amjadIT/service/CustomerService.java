package in.amjadIT.service;

import org.springframework.stereotype.Service;

import in.amjadIT.dto.CustomerDTO;
import in.amjadIT.dto.ResetPwdDTO;
import in.amjadIT.reponse.AuthResponse;

@Service
public interface CustomerService {

	public Boolean isEmailUnique(String email);

	public Boolean register(CustomerDTO customerDto);

	public Boolean resetPwd(ResetPwdDTO resetPwdDto);
	
	public Boolean forgotPwd(String email);

	public CustomerDTO getCustomerByEmail(String email);

	public AuthResponse login(CustomerDTO customerDto);
	
	
}

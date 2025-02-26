package in.amjadIT.service;

import java.util.Collections;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.amjadIT.dto.CustomerDTO;
import in.amjadIT.dto.ResetPwdDTO;
import in.amjadIT.entities.Customer;
import in.amjadIT.mapper.CustomerMapper;
import in.amjadIT.repo.CustomerRepo;
import in.amjadIT.reponse.AuthResponse;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Autowired
	private EmailService emailService;

	@Autowired
	private AuthenticationManager authManager;

	@Override
	public Boolean isEmailUnique(String emailId) {
		Customer byEmail = customerRepo.findByEmail(emailId);
		return byEmail == null;
	}

	@Override
	public Boolean register(CustomerDTO customDto) {

		String orgPwd = generateRandomPassword();
		String encodedPwd = pwdEncoder.encode(orgPwd);

		Customer entity = CustomerMapper.convertToEntity(customDto);
		entity.setPwd(encodedPwd);
		entity.setPwdUpdated("NO");

		Customer savedEntity = customerRepo.save(entity);

		if (savedEntity.getId() != null) {
			String subject = "Registration success";
			String body = "Your Login Password " + orgPwd;
			return emailService.sendEmail(customDto.getEmail(), subject, body);
		}

		return false;
	}

	@Override
	public Boolean resetPwd(ResetPwdDTO pwdDto) {

		Customer c = customerRepo.findByEmail(pwdDto.getEmail());

		if (c != null) {
			String newPassword = pwdDto.getNewPwd();
			String encodedPwd = pwdEncoder.encode(newPassword);
			c.setPwd(encodedPwd);
			c.setPwdUpdated("YES");

			customerRepo.save(c);
			return true;
		}

		return false;
	}

	@Override
	public CustomerDTO getCustomerByEmail(String emailId) {

		Customer c = customerRepo.findByEmail(emailId);

		if (c != null) {
			return CustomerMapper.convertToDto(c);
		}
		return null;
	}

	@Override
	public AuthResponse login(CustomerDTO customerDto) {

		AuthResponse authResponse = null;

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(customerDto.getEmail(),
				customerDto.getPwd());

		Authentication authenticate = authManager.authenticate(authToken);

		if (authenticate.isAuthenticated()) {
			authResponse = new AuthResponse();
			Customer c = customerRepo.findByEmail(customerDto.getEmail());
			authResponse.setCustomerDto(customerDto);
			authResponse.setToken("");

		}
		return authResponse;
	}

	@Override
	public Boolean forgotPwd(String email) {
		Customer c = customerRepo.findByEmail(email);
		if (c != null) {
			String subject = "Reset Password request";
			String body = "Temp file";
			emailService.sendEmail(email, subject, body);
			return true;
		}
		return false;

	}

	private String generateRandomPassword() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		StringBuilder password = new StringBuilder();

		Random random = new Random();

		for (int i = 0; i < 5; i++) {
			int index = (int) (random.nextFloat() * SALTCHARS.length());
			password.append(SALTCHARS.charAt(index));
		}

		String save = password.toString();

		return save;
	}

}

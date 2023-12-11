package hu.cubix.logistics.security;

import hu.cubix.logistics.dto.LoginDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTLoginController {

	final
	AuthenticationManager authenticationManager;
	
	final
	JwtService jwtService;

	public JWTLoginController(AuthenticationManager authenticationManager, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}


	@PostMapping("/api/login")
	public String login(@RequestBody LoginDTO login) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		return jwtService.creatJwtToken((UserDetails)authentication.getPrincipal());
	}
}

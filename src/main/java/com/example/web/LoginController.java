package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.facade.AdminFacade;
import com.example.facade.ClientFacade;
import com.example.facade.CompanyFacade;
import com.example.facade.CustomerFacade;
import com.example.service.ClientType;
import com.example.service.LoginManager;
import com.example.service.SessionManager;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
	
	@Autowired
	LoginManager lm;
	@Autowired
	SessionManager sm;
	
	@PostMapping("/{email}/{password}/{client}")
	public ResponseEntity<String> login(@PathVariable String email, @PathVariable String password, @PathVariable ClientType client) {
		ClientFacade clientFacade = lm.login(email, password, client);
		if (clientFacade != null)
			return ResponseEntity.ok(sm.generateSession(clientFacade));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@DeleteMapping("/logout/{token}")
	public ResponseEntity<?> logout(@PathVariable String token) {
		sm.removeSession(token);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{token}")
	public ResponseEntity<String> getClientType(@PathVariable String token) {
		ClientFacade clientFacade = sm.getSessions().get(token).getFacade();
		if (clientFacade instanceof CustomerFacade)
			return ResponseEntity.ok("CUSTOMER");
		if (clientFacade instanceof CompanyFacade)
			return ResponseEntity.ok("COMPANY");
		if (clientFacade instanceof AdminFacade)
			return ResponseEntity.ok("ADMIN");
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@GetMapping("/check/{clientType}/{email}")
	public ResponseEntity<Boolean> isEmailAvailable(@PathVariable String email, @PathVariable ClientType clientType) {
		return ResponseEntity.ok(lm.isEmailAvailable(email, clientType));
	}
	
}

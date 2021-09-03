package gmc.project.textnchat.users.controllers;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private final Environment environment;
	
	public UserController(Environment environment) {
		super();
		this.environment = environment;
	}


	@GetMapping("/status/check")
	public String checkStatus() {
		return "Server Working in port : " + environment.getProperty("local.server.port");
	}

}
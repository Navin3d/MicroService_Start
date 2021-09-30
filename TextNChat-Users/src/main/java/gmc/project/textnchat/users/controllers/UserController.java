package gmc.project.textnchat.users.controllers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.project.textnchat.users.models.CreateUserRequestModel;
import gmc.project.textnchat.users.models.CreateUserResponseModel;
import gmc.project.textnchat.users.service.UserService;
import gmc.project.textnchat.users.shared.UserDto;

@RestController
@RequestMapping("/user")
public class UserController {

	private final Environment environment;
	private final UserService userService;

	public UserController(Environment environment, UserService userService) {
		super();
		this.environment = environment;
		this.userService = userService;
	}

	@GetMapping("/status/check")
	public String checkStatus() {
		return "Server Working in port : " + environment.getProperty("local.server.port");
	}

	@PostMapping("/signin")
	public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody CreateUserRequestModel createUserRequestModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = modelMapper.map(createUserRequestModel, UserDto.class);
		
		UserDto createdUser = userService.createNewUser(userDto);
		
		CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);
				
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);		
	}

}

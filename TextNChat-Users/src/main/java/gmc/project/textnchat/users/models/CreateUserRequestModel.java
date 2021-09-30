package gmc.project.textnchat.users.models;

import lombok.Data;

@Data
public class CreateUserRequestModel {

	private String userName;
	private String email;
	private String password;
	private String confirmPassword;
	
}

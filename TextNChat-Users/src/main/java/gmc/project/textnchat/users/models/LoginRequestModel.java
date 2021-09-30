package gmc.project.textnchat.users.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestModel {
	private String email;
	private String password;
}

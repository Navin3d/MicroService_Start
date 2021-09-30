package gmc.project.textnchat.users.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResponseModel {
	private String userId;
	private String userName;
	private String email;
}

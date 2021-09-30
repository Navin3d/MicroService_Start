package gmc.project.textnchat.users.shared;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDto implements Serializable {

	private static final long serialVersionUID = -3755701018656892210L;
	
	private String userId;
	private String userName;
	private String email;
	private String password;
	private String encryptedPassword;

}

package gmc.project.textnchat.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import gmc.project.textnchat.users.shared.UserDto;

public interface UserService extends UserDetailsService {
	UserDto createNewUser(UserDto userDetails);
	UserDto getUserByUserName(String userName);
	UserDto getUserByEmail(String email);
}

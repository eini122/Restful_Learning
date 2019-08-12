package App.test.app.test.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import App.test.app.test.shared.dto.UserDto;

public interface UserService extends UserDetailsService{
	UserDto createUser(UserDto user);
}

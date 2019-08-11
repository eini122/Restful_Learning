package App.test.app.test.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import App.test.app.test.service.UserService;
import App.test.app.test.shared.dto.UserDto;
import App.test.app.test.ui.model.request.UserDetailsRequestModel;
import App.test.app.test.ui.model.response.UserResponse;

@RestController
@RequestMapping("users") //http://localhost:8080/users
public class userController {
	@Autowired
	UserService userService;
	
	@GetMapping
	public String getUser() {
		return "get";
	}
	
	@PostMapping
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) {
		UserResponse returnValue = new UserResponse();
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		
		return returnValue;
	}
	
	@PutMapping
	public String postUser() {
		return "update";
	}
	
	@DeleteMapping
	public String deleteUser() {
		return "Delete";
	}
}

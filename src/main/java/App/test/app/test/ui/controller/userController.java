package App.test.app.test.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import App.test.app.test.exceptions.UserServiceException;
import App.test.app.test.service.UserService;
import App.test.app.test.shared.dto.UserDto;
import App.test.app.test.ui.model.request.UserDetailsRequestModel;
import App.test.app.test.ui.model.response.ErrorMessages;
import App.test.app.test.ui.model.response.UserResponse;

@RestController
@RequestMapping("users") //http://localhost:8080/users
public class userController {
	@Autowired
	UserService userService;
	
	@GetMapping(path = "/{id}",
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public UserResponse getUser(@PathVariable String id) {
		UserResponse returnValue = new UserResponse();
		
		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnValue);
	
		
		return returnValue;
	}
	
	@PostMapping (
			consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserResponse returnValue = new UserResponse();
		
		if(userDetails.getFirstName().isEmpty()) 
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
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

package App.test.app.test.ui.controller;

import org.springframework.web.bind.annotation.*;

import App.test.app.test.ui.model.request.UserDetailsRequestModel;
import App.test.app.test.ui.model.response.UserResponse;

@RestController
@RequestMapping("users")
public class userController {
	
	@GetMapping
	public String getUser() {
		return "get";
	}
	
	@PostMapping
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) {
		return null;
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

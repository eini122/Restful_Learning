package App.test.app.test.ui.controller;

import java.util.List;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import App.test.app.test.exceptions.UserServiceException;
import App.test.app.test.service.AddressService;
import App.test.app.test.service.UserService;
import App.test.app.test.shared.dto.AddressDTO;
import App.test.app.test.shared.dto.UserDto;
import App.test.app.test.ui.model.request.UserDetailsRequestModel;
import App.test.app.test.ui.model.response.AddressesRest;
import App.test.app.test.ui.model.response.ErrorMessages;
import App.test.app.test.ui.model.response.OpeartionStatusModel;
import App.test.app.test.ui.model.response.RequestOperationName;
import App.test.app.test.ui.model.response.RequestOperationStatus;
import App.test.app.test.ui.model.response.UserResponse;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class userController {
	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	AddressService addressesService;

	// HTTP get request
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserResponse getUser(@PathVariable String id) {
		// the order of Json response
		UserResponse returnValue = new UserResponse();

		// get the user information by service layer by user id
		UserDto userDto = userService.getUserByUserId(id);
		// copy the details from database to return value by userDto class
		BeanUtils.copyProperties(userDto, returnValue);

		// return the data
		return returnValue;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, 
			MediaType.APPLICATION_JSON_VALUE }, 
			produces = {
			MediaType.APPLICATION_XML_VALUE, 
			MediaType.APPLICATION_JSON_VALUE })
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) 
			throws Exception {
		UserResponse returnValue = new UserResponse();

		if (userDetails.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

//		UserDto userDto = new UserDto();
//		BeanUtils.copyProperties(userDetails, userDto);
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);
		//BeanUtils.copyProperties(createdUser, returnValue);
		returnValue = modelMapper.map(createdUser, UserResponse.class);

		return returnValue;
	}

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {

		UserResponse returnValue = new UserResponse();

		if (userDetails.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

		UserDto updatedUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);

		return returnValue;
	}

	@DeleteMapping(path = "/{id}", 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OpeartionStatusModel deleteUser(@PathVariable String id) {

		OpeartionStatusModel returnValue = new OpeartionStatusModel();

		userService.deleteUser(id);

		returnValue.setOperationName(RequestOperationName.DELETE.name());
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

		return returnValue;
	}

	@GetMapping(
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<UserResponse> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		
		List<UserResponse> returnValue = new ArrayList<>();
		
		if(page > 1) page-=1;
		
		List<UserDto>users = userService.getUsers(page, limit);
		
		for(UserDto userDto: users) {
			UserResponse userModel = new UserResponse();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}
		
		return returnValue;
	}

	@GetMapping(
			path="/{id}/addresses",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<AddressesRest> getUserAddresses(@PathVariable String id)
	{
		List<AddressesRest> returnValue = new ArrayList<>();
		
		List<AddressDTO> addressesDto = addressesService.getAddresses(id);
		
		if(addressesDto != null && !addressesDto.isEmpty()) {
			//Java.lang.reflect
			//ModelMapper modelMapper = new ModelMapper();
			Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
			returnValue = new ModelMapper().map(addressesDto, listType);
		}
		
		return returnValue;
	}
	
	@GetMapping(
			path="/{userId}/addresses/{addressId}",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public AddressesRest getUserAddress(@PathVariable String addressId)
	{
		AddressesRest returnValue = new AddressesRest();
		
		AddressDTO addressesDto = addressService.getAddress(addressId);
		
		if(addressesDto != null) {
			returnValue = new ModelMapper().map(addressesDto, AddressesRest.class);
		}
		
		return returnValue;
	}
}

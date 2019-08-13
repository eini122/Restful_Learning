package App.test.app.test.service.Impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import App.test.app.test.io.entity.UserEntity;
import App.test.app.test.io.repositories.UserRepository;
import App.test.app.test.service.UserService;
import App.test.app.test.shared.Util;
import App.test.app.test.shared.dto.UserDto;

@Service
public class UserServceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Util util;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto user) {
		//ensure email is unique
		if(userRepository.findByEmail(user.getEmail())!=null) throw new RuntimeException("email already exists");
		
		//copy user information to UserEntity
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		//secure password
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		//ensure userId is unique
		String userId;
		while(true) {
			userId = util.generateUserId(30);
			if(userRepository.findByUserId(userId) != null) continue;
			else break;
		}
		userEntity.setUserId(userId);
		
		UserEntity storedUserDetails = userRepository.save(userEntity);
		
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);
		
		return returnValue;
	}
	
	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if(userEntity == null) throw new UsernameNotFoundException(email);
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

}

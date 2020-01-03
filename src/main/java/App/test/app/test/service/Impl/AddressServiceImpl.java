package App.test.app.test.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import App.test.app.test.io.entity.AddressEntity;
import App.test.app.test.io.entity.UserEntity;
import App.test.app.test.io.repositories.AddresRepository;
import App.test.app.test.io.repositories.UserRepository;
import App.test.app.test.service.AddressService;
import App.test.app.test.shared.dto.AddressDTO;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddresRepository addressRepository;
	
	@Override
	public List<AddressDTO> getAddresses(String userId) {
		ModelMapper modelMapper = new ModelMapper();
		List<AddressDTO> returnValue = new ArrayList<>();
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null) return returnValue;
		
		Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
		
		for(AddressEntity addressEntity:addresses) {
			returnValue.add(modelMapper.map(addressEntity, AddressDTO.class));
		}
		
		return returnValue;
	}

	@Override
	public AddressDTO getAddress(String addressId) {
		
		AddressEntity address = addressRepository.findByAddressId(addressId);
		
		if(address == null) {
			return new AddressDTO();
		}
		return new ModelMapper().map(address, AddressDTO.class);
	}

}

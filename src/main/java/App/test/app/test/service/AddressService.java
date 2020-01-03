package App.test.app.test.service;

import java.util.List;

import App.test.app.test.shared.dto.AddressDTO;

public interface AddressService {
	List<AddressDTO> getAddresses(String userId);
	AddressDTO getAddress(String addressId);
}

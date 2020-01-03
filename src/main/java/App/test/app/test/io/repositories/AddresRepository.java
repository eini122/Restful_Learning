package App.test.app.test.io.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import App.test.app.test.io.entity.AddressEntity;
import App.test.app.test.io.entity.UserEntity;

@Repository
public interface AddresRepository extends CrudRepository<AddressEntity, Long> {
	List<AddressEntity> findAllByUserDetails(UserEntity userEntity);
	AddressEntity findByAddressId(String addressId);
}

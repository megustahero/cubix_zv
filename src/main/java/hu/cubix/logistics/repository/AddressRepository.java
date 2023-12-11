package hu.cubix.logistics.repository;

import hu.cubix.logistics.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
	
	@Query("SELECT a FROM Address a WHERE id = :id")
	public Address listById(Long id);
	
}

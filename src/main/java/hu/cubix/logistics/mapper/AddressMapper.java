package hu.cubix.logistics.mapper;

import hu.cubix.logistics.dto.AddressDTO;
import hu.cubix.logistics.model.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
	
	Address dtoToModel(AddressDTO dto); 
	AddressDTO modelToDto(Address model);
	
	List<Address> listDtoToModel(List<AddressDTO> dtoList);
	List<AddressDTO> listModelToDto(List<Address> modelList);
	

}

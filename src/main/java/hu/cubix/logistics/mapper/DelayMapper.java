package hu.cubix.logistics.mapper;

import hu.cubix.logistics.dto.DelayDTO;
import hu.cubix.logistics.model.Delay;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DelayMapper {
	
	Delay dtoToModel(DelayDTO dto);
	DelayDTO modelToDto(Delay model);

}

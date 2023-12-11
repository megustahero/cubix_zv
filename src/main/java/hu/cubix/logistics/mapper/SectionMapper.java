package hu.cubix.logistics.mapper;

import hu.cubix.logistics.dto.SectionDTO;
import hu.cubix.logistics.model.Section;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SectionMapper {
	
	Section dtoToModel(SectionDTO dto);
	SectionDTO modelToDto(Section model);

}

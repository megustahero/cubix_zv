package hu.cubix.logistics.mapper;

import hu.cubix.logistics.dto.SectionDTO;
import hu.cubix.logistics.dto.TransportPlanDTO;
import hu.cubix.logistics.model.Section;
import hu.cubix.logistics.model.TransportPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransportPlanMapper {

	TransportPlan dtoToModel(TransportPlanDTO dto);	
	TransportPlanDTO modelToDto(TransportPlan model);
	
	// To avoid infinite recursion
	@Mapping(target = "transportPlan", ignore = true)
	SectionDTO sectionToSectionDTO(Section model);
	
	TransportPlanDTO modelToDtoWithoutSections(TransportPlan model);
	
	List<SectionDTO> sectionListToSectionDTOList(List<Section> model);
	
}

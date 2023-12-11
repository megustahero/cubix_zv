package hu.cubix.logistics.mapper;

import hu.cubix.logistics.dto.MilestoneDTO;
import hu.cubix.logistics.model.Milestone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MilestoneMapper {

	Milestone dtoToModel(MilestoneDTO dto);
	MilestoneDTO modelToDto(Milestone model);
}

package hu.cubix.logistics.web;

import hu.cubix.logistics.dto.MilestoneDTO;
import hu.cubix.logistics.mapper.MilestoneMapper;
import hu.cubix.logistics.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/milestone")
public class MilestoneController {
	
	@Autowired
	MilestoneMapper milestoneMapper;
	
	@Autowired
	MilestoneService milestoneService;
	
	
	@PostMapping
	public ResponseEntity<MilestoneDTO> addNew(@RequestBody @Validated MilestoneDTO milestone) {
		
		try 
		{
			return ResponseEntity.ok(milestoneMapper.modelToDto(milestoneService.addNew(milestoneMapper.dtoToModel(milestone))));
		}
		catch(Exception exc)
		{
			System.out.println(exc.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

}

package hu.cubix.logistics.web;

import hu.cubix.logistics.dto.SectionDTO;
import hu.cubix.logistics.mapper.SectionMapper;
import hu.cubix.logistics.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/section")
public class SectionController {
	
	@Autowired
	SectionMapper sectionMapper;
	
	@Autowired
	SectionService sectionService;
	
	@PostMapping
	public ResponseEntity<SectionDTO> addNew(@RequestBody @Validated SectionDTO section) {
		
		try
		{
			SectionDTO saved = sectionMapper.modelToDto(sectionService.addNew(sectionMapper.dtoToModel(section)));
			return ResponseEntity.ok(saved);
		}
		catch(Exception exc)
		{
			System.out.println(exc.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}
}

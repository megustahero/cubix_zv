package hu.cubix.logistics.service;

import hu.cubix.logistics.model.Section;
import hu.cubix.logistics.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SectionService {

	@Autowired
	SectionRepository sectionRepository;
	
	
	@Transactional
	public Section addNew(Section section) {
		
		return sectionRepository.save(section);
	}
	
	@Transactional
	public List<Section> hasMilestoneWithId(Long planId, Long milestoneId) {
		
		return sectionRepository.hasMilestoneWithId(planId, milestoneId);
	}
	
}

package hu.cubix.logistics.service;

import hu.cubix.logistics.model.Milestone;
import hu.cubix.logistics.repository.AddressRepository;
import hu.cubix.logistics.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MilestoneService {

	@Autowired
	MilestoneRepository milestoneRepository;

	@Autowired
	AddressRepository addressRepository;

	
	@Transactional
	public Milestone addNew(Milestone milestone) {
		
		return milestoneRepository.save(milestone);
	}
	
	@Transactional
	public Boolean milestoneExists(Long id) {
		
		return milestoneRepository.existsById(id);
	}

	@Transactional
	public Milestone getById(Long milestoneId) {

		return milestoneRepository.getReferenceById(milestoneId);
	}
}

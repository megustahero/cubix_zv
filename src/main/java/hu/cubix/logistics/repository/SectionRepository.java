package hu.cubix.logistics.repository;

import hu.cubix.logistics.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long>, JpaSpecificationExecutor<Section> {

	
	@Query("SELECT s FROM Section s JOIN FETCH s.transportPlan JOIN FETCH s.fromMilestone JOIN FETCH s.toMilestone WHERE s.transportPlan.id = :planId AND (s.fromMilestone.id = :milestoneId OR s.toMilestone.id = :milestoneId) ORDER BY s.number ASC")
	List<Section> hasMilestoneWithId(Long planId, Long milestoneId);
	
}
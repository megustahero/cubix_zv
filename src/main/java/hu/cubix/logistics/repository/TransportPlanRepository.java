package hu.cubix.logistics.repository;

import hu.cubix.logistics.model.TransportPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long>, JpaSpecificationExecutor<TransportPlan> {

	@Query("SELECT t FROM TransportPlan t JOIN FETCH t.sections WHERE t.id = :id")
	TransportPlan getOne(Long id);
		
}

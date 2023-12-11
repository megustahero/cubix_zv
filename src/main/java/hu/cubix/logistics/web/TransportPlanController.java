package hu.cubix.logistics.web;

import hu.cubix.logistics.dto.DelayDTO;
import hu.cubix.logistics.dto.TransportPlanDTO;
import hu.cubix.logistics.mapper.DelayMapper;
import hu.cubix.logistics.mapper.MilestoneMapper;
import hu.cubix.logistics.mapper.TransportPlanMapper;
import hu.cubix.logistics.service.TransportPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.validation.annotation.Validated;
import org.springframework.stereotype.Controller;


@Controller
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

	@Autowired
	TransportPlanService transportPlanService;

	@Autowired
	TransportPlanMapper transportPlanMapper;

	@Autowired
	DelayMapper delayMapper;

	@PostMapping("/{planId}/delay")
	@ResponseBody
	public ResponseEntity<TransportPlanDTO> addDelayToMilestone(@RequestBody @Validated DelayDTO delay, @PathVariable Long planId) {

		try {
			TransportPlanDTO updated = transportPlanMapper.modelToDto(transportPlanService.addDelay(planId, delayMapper.dtoToModel(delay)));
			return ResponseEntity.ok(updated);
		} catch (HttpClientErrorException exc) {
			return ResponseEntity.status(exc.getStatusCode()).build();
		}
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<TransportPlanDTO> addNew(@RequestBody @Validated TransportPlanDTO plan) {

		try {
			TransportPlanDTO saved = transportPlanMapper.modelToDto(transportPlanService.addNew(transportPlanMapper.dtoToModel(plan)));
			return ResponseEntity.ok(saved);
		} catch (Exception exc) {
			System.out.println(exc.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}
}

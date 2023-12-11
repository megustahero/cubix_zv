package hu.cubix.logistics.web;

import hu.cubix.logistics.dto.AddressDTO;
import hu.cubix.logistics.mapper.AddressMapper;
import hu.cubix.logistics.model.Address;
import hu.cubix.logistics.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressRestController {
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	AddressMapper addressMapper;
	
	
	@GetMapping
	public List<AddressDTO> listAll() {
		
		return addressMapper.listModelToDto(addressService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AddressDTO> getOneById(@PathVariable Long id) {
		
		AddressDTO found = addressMapper.modelToDto(addressService.getOneById(id));
		
		if(found != null)
		{
			return ResponseEntity.ok(found);
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
		
	}

	@PostMapping
	public ResponseEntity<AddressDTO> addNew(@RequestBody @Validated AddressDTO address) {
		
		try 
		{
			AddressDTO saved = addressMapper.modelToDto(addressService.addNew(addressMapper.dtoToModel(address)));
			return ResponseEntity.ok(saved);
		}
		catch(Exception exc)
		{
			return ResponseEntity.badRequest().build();
		}
		
	}
	
	@PostMapping("/search")
	public ResponseEntity<List<AddressDTO>> searchByExample(@RequestBody AddressDTO example, 
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "0") int size, 
			@RequestParam(defaultValue = "id,asc") String sort) {
		
		if(example == null)
		{
			return ResponseEntity.badRequest().build();
		}
		
		if(size == 0)
		{
			size = Integer.MAX_VALUE;
		}
		
		
		// Set sorting
		Pageable pageable;
		
		if(sort.contains(",asc") || !sort.contains(",desc"))
		{
			pageable = PageRequest.of(page, size, Sort.by(sort.split(",")[0]).ascending());
		}
		else
		{
			pageable = PageRequest.of(page, size, Sort.by(sort.split(",")[0]).descending());
		}
		
		// Get the actual page
		Page<Address> result = addressService.searchByExample(addressMapper.dtoToModel(example), pageable);
		
		// Set total count header
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("X-Total-Count", String.valueOf(result.getTotalElements()));
		
		return ResponseEntity
				.ok()
				.headers(responseHeaders)
				.body(addressMapper.listModelToDto(result.getContent()));
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<AddressDTO> deleteAddress(@PathVariable Long id) {
		
		try
		{
			addressService.deleteAddressById(id);
		}
		catch(Exception exc)
		{
			System.out.println(exc.getMessage());
		}

		//Finally
		return ResponseEntity.ok(null);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AddressDTO> overwriteExisting(@RequestBody AddressDTO newAddress, @PathVariable Long id) {
		
		if(newAddress.getId() != null && newAddress.getId() != id)
		{
			return ResponseEntity.badRequest().build();
		}
		else if(!addressService.isPresent(id))
		{
			return ResponseEntity.notFound().build();
		}
		
		try 
		{
			newAddress.setId(id);
			AddressDTO saved = addressMapper.modelToDto(addressService.addNew(addressMapper.dtoToModel(newAddress)));
			return ResponseEntity.ok(saved);
		}
		catch(Exception exc)
		{
			return ResponseEntity.badRequest().build();
		}
		
	}
	
	
	
}

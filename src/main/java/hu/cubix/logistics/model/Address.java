package hu.cubix.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String countryIsoCode;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String street;
	
	@NotBlank
	private String postcode;
	
	@NotBlank
	private String houseNumber;
	
	private double latitude;
	
	private double longitude;
	
	

	public Address() {
		
	}

	public Address(Long id, @NotBlank String countryIsoCode, @NotBlank String city, @NotBlank String street,
			@NotBlank String postcode, @NotBlank String houseNumber, double latitude, double longitude) {

		this.id = id;
		this.countryIsoCode = countryIsoCode;
		this.city = city;
		this.street = street;
		this.postcode = postcode;
		this.houseNumber = houseNumber;
		this.latitude = latitude;
		this.longitude = longitude;
	}



	public Long getId() {
		return id;
	}

	public String getCountryIsoCode() {
		return countryIsoCode;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setCountryIsoCode(String countryIsoCode) {
		this.countryIsoCode = countryIsoCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setId(Long id) {
		this.id = id;
	}	

}

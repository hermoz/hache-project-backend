package hmm.architecturestudio.management.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RoleDto {
	
	@NotBlank
	private Long id;
    private String name;
    
    private Set<PrivilegeDto> privileges;
    
    public RoleDto() {
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * Privileges getters and setters
	 */
	
	// We use JsonIgnore to exclude privileges from response
	@JsonIgnore
	public Set<PrivilegeDto> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<PrivilegeDto> privileges) {
		this.privileges = privileges;
	}
}

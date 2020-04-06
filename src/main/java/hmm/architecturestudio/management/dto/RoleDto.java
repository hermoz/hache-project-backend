package hmm.architecturestudio.management.dto;

import java.util.Collection;

public class RoleDto {
	
	private Long id;
    private String name;
    
    private Collection<PrivilegeDto> privileges;
    
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
	public Collection<PrivilegeDto> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Collection<PrivilegeDto> privileges) {
		this.privileges = privileges;
	}
}

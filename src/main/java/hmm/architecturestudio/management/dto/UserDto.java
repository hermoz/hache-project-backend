package hmm.architecturestudio.management.dto;

import java.util.Collection;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

public class UserDto {

	private Long id;
	
	@NotBlank
    private String username;
	
	// Password can be empty or null in updates
    private String password;
	
	@NotBlank
    private String email;
	
	@NotBlank
    private String name;
	
	@NotBlank
    private String lastname;
	
	@NotBlank
    private String phone;
	
	@NotBlank
    private String address;
    
	@NotNull
    private Set<RoleDto> roles;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 
	 * Roles getters and setters 
	 */
	
	public Set<RoleDto> getRoles() {
        return roles;
    }
	
	public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }
    
}

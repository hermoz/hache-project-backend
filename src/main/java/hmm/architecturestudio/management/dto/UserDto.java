package hmm.architecturestudio.management.dto;

import java.util.Collection;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

public class UserDto {

	private Long id;
	
	@NotBlank
    private String username;
	
	@NotBlank
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
    private Collection<RoleDto> roles;
    
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
	public String getPassword() {
		return password;
	}
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
	
	public Collection<RoleDto> getRoles() {
		return roles;
	}
	public void setRoles(Collection<RoleDto> roles) {
		this.roles = roles;
	}
    
}

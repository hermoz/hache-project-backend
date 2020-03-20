package hmm.architecturestudio.management.model;

import java.util.Collection;

import javax.persistence.*;

@Entity
public class User {
	
    @Id
    // Automatic value generation for each new entity object
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /*
     * Log in with username and password
     */

    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String address;
    
    @ManyToMany
    /*
     * New table´s creation where user_id and role_id are going to be related
     */
//    @JoinTable(name=users_roles")
    
    private Collection<Role> roles;
    
    public User() {
    	
    }

    // Getter and setters 
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
	public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

}

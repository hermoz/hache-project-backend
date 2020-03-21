package hmm.architecturestudio.management.model;

import java.util.Set;

import javax.persistence.*;

/*
 * Consideration of both User-Role and Role-Privilege relationships many-to-many bidirectional
 */
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
    
    
    /*
     * Configuration of table "users_roles" where user_id and role_id are related
     * 
     * Properties used on JoinColumn:
     * - name of the column inside the table
     * - referencedColumnName: indicates the column on which the Join of the other table will be performed
     * - Foreing Key
     */
    @ManyToMany
    @JoinTable(
    		name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_users_roles_user_id")),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_users_roles_role_id"))
    )
    
    private Set<Role> roles;
    
    // Constructor
    public User() {
    	
    }

    // Getters and setters 
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", name="
				+ name + ", lastname=" + lastname + ", phone=" + phone + ", address=" + address + ", roles=" + roles
				+ "]";
	}

	
	

}

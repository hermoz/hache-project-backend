package hmm.architecturestudio.management.model;

import java.util.Set;

import javax.persistence.*;


/**
 * Our Role represents the high-level roles of users in the systema
 * Each role has different priviledges
 * Consideration of both User-Role and Role-Privilege relationships many-to-many bidirectional
 * @author Herminia
 *
 */
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    

    /**
     * We use a @ManytoMany annotation to model a many-value relation-ship thar requires a join table.
     * We have a user-role many-to-many relationship
     */
    /**
     * 
     * Configuration of table "roles_privileges" where role_id and privilege_id are related
     * 
     * Properties used on JoinColumn:
     * - name of the column inside the table
     * - referencedColumnName: indicates the column on which the Join of the other table will be performed
     * - Foreing Key
     */

    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(
        name = "roles_privileges",
        joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_roles_privileges_role_id")),
        inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_roles_privileges_privilege_id"))
    )
    private Set<Privilege> privileges;

    
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    
    
    // Constructor
    public Role() {
    	
    }
    
    // Getters and setters
    
    public Role(String name) {
        this.name = name;
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

	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", users=" + users + "]";
	}
	
    
    


}
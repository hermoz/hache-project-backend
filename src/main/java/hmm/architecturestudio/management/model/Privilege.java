package hmm.architecturestudio.management.model;

import javax.persistence.*;
import java.util.Set;


/**
 * Privilege represents a lower level compared to roles, asociated to authority in the system
 * Consideration of both User-Role and Role-Privilege relationships many-to-many bidirectional
 * @author Herminia
 *
 */
@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    
    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;

    public Privilege() {
    }

    public Privilege(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

	@Override
	public String toString() {
		return "Privilege [id=" + id + ", name=" + name + ", roles=" + roles + "]";
	}


}

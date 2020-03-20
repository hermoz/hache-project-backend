package hmm.architecturestudio.management.model;

import java.util.Collection;

import javax.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    
    /**
     * We use a @ManytoMany annotation to model a many-value relation-ship thar requires a join table.
     * We have a user-role many-to-may relationship
     */
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
    
    public Role() {
    	
    }
    
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
	
	public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
    
    


}
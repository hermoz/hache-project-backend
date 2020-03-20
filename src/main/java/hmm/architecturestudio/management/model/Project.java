package hmm.architecturestudio.management.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Project {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;
    
    @Column(nullable = false)
    private String location;
    
    /**
     * Project types are defined on another entity and is defined as a one-to-one relation
     */
    @OneToOne
    @JoinColumn(name="type_id")
    private ProjectType type;
    
    /*
     * Projects are associated with customers as a many-to-one assotiation where a project
     * is only going to be related with one customer
     */
    @ManyToOne
    @JoinColumn(name="customer_id")
    
    private Customer customer;
    
    public Project() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ProjectType getType() {
		return type;
	}

	public void setType(ProjectType type) {
		this.type = type;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}

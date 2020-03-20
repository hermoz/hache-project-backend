package hmm.architecturestudio.management.model;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Customer {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String cif;

    @Column(nullable = false)
    private String taxResidence;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String population;

    @Column(nullable = false, unique = true)
    private String email;
    
    /*
     * Setting one to many assotiation as a project is only going to be 
     * related to only one customer.
     * We defined a persistence collection where the collection is saved as a one to many relation in the associated table 
     */
    @OneToMany(mappedBy = "customer")
    private Set<Project> projects;
    
    public Customer() {
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

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getTaxResidence() {
		return taxResidence;
	}

	public void setTaxResidence(String taxResidence) {
		this.taxResidence = taxResidence;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPopulation() {
		return population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
	public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
    

}

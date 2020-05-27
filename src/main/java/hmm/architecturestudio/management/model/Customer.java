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
     * A project is only going to be related to only one customer.
     * We defined a persistence collection where the collection is saved as a one to many relation in the associated table.
     * As we define @OneToMany(mappedBy = "customer") once a customer is deleted, its information is deleted too (cascade)
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Project> projects;
    
    // Constructor
    public Customer() {
    }

    // Getters and setters
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

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", cif=" + cif + ", taxResidence=" + taxResidence + ", phone="
				+ phone + ", population=" + population + ", email=" + email + ", projects=" + projects + "]";
	}
    

}

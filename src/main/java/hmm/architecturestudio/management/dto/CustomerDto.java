package hmm.architecturestudio.management.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

public class CustomerDto {

    private Long id;

    /**
     * Field validation
     */
    
    @NotBlank
    private String name;

    @NotBlank
    private String cif;

    @NotBlank
    private String taxResidence;

    @NotBlank
    private String phone;

    @NotBlank
    private String population;

    @NotBlank
    private String email;

  
    @JsonIgnoreProperties("customer")
    private Set<ProjectDto> projects;

    /*
     * Customer getters and setters
     */
    
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

    /**
     * Project getter and setter
     */
    public Set<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectDto> projects) {
        this.projects = projects;
    }
}

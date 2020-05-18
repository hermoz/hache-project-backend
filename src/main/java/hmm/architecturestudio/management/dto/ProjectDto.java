package hmm.architecturestudio.management.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




public class ProjectDto {

    private Long id;

    /**
     * Validations
     */
    
    @NotBlank
    private String title;
    
    @NotNull
    private ProjectTypeDto type;

    @NotBlank
    private String location;

 
    /**
     * @JsonIgnoreProperties is used to avoid circular errors
     */
    @NotNull
    @JsonIgnoreProperties("projects")
    private CustomerDto customer;

    /*
     * Project getters and setters
     */
    
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
    
    public ProjectTypeDto getType() {
        return type;
    }

    public void setType(ProjectTypeDto type) {
        this.type = type;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Customer getter and setter
     */
    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }
}

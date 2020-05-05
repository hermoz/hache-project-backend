package hmm.architecturestudio.management.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



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


    private CustomerDto customerDto;

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
    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }
}

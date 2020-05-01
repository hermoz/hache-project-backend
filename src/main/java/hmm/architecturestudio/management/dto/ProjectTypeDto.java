package hmm.architecturestudio.management.dto;

import javax.validation.constraints.NotBlank;

public class ProjectTypeDto {

    @NotBlank
    private Long id;

    private String name;

    /*
     * ProjectType getters and setters
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
}

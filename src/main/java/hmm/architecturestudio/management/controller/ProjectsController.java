package hmm.architecturestudio.management.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hmm.architecturestudio.management.dto.ProjectTypeDto;
import hmm.architecturestudio.management.model.ProjectType;
import hmm.architecturestudio.management.service.ProjectsService;

/*
 * Anotation with @RestController because an API REST
 * and mapping the controller with "/projects" all REST methods
 * and then each method has its own endpoint
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectsController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProjectsService projectsService;

    /*
	 * Get Project by Type
	 */
    @GetMapping("/types")
    @ResponseBody
    public List<ProjectTypeDto> getProjectTypes() {
        List<ProjectType> projectTypes = null;
        projectTypes = projectsService.getProjectTypes();
        return projectTypes.stream().map(this::convertProjectTypeToDto).collect(Collectors.toList());
    }

    
    /*
     * Convert entity to DTO
     */
    private ProjectTypeDto convertProjectTypeToDto(ProjectType projectType) {
        ProjectTypeDto projectTypeDto = modelMapper.map(projectType, ProjectTypeDto.class);
        return projectTypeDto;
    }

}

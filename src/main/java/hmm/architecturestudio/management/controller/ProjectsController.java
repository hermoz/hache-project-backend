package hmm.architecturestudio.management.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hmm.architecturestudio.management.dto.ProjectDto;
import hmm.architecturestudio.management.dto.ProjectTypeDto;
import hmm.architecturestudio.management.exception.PrivilegesException;
import hmm.architecturestudio.management.exception.ValidationServiceException;
import hmm.architecturestudio.management.model.Project;
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
	 * Get Project
	 */
    @GetMapping
    @ResponseBody
    public List<ProjectDto> getProjects() {
        List<Project> projects = null;
        try {
            projects = projectsService.findAll();
        } catch (PrivilegesException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }

        return projects.stream().map(this::convertProjectToDto).collect(Collectors.toList());
    }
    
	/*
	 * Get Project by Id
	 */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ProjectDto getProject(@PathVariable("id") Long id) {
        Optional<Project> project = null;
        try {
            project = projectsService.findById(id);
        } catch (PrivilegesException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }

        if (!project.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
        }

        return convertProjectToDto(project.get());
    }
    
    /*
     * Create Project
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ProjectDto createProject(@Valid @RequestBody ProjectDto projectDto) {
        Project project = convertProjectDtoToEntity(projectDto);
        Project createdProject = null;

        try {
            createdProject = projectsService.createProject(project);
        } catch (PrivilegesException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ValidationServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return convertProjectToDto(createdProject);
    }
    
    /*
     * Update Project
     */
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ProjectDto updateProject(@Valid @RequestBody ProjectDto projectDto) {

        Project project = convertProjectDtoToEntity(projectDto);
        Project updatedProject = null;

        try {
            updatedProject = projectsService.updateProject(project);
        } catch (PrivilegesException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ValidationServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return convertProjectToDto(updatedProject);
    }
    
    /*
     * Delete User
     */
    
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable("id") Long id) {
        try {
            projectsService.deleteById(id);
        } catch (PrivilegesException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ValidationServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    
    /*
     * Convert ProjectType to DTO
     */
    private ProjectTypeDto convertProjectTypeToDto(ProjectType projectType) {
        ProjectTypeDto projectTypeDto = modelMapper.map(projectType, ProjectTypeDto.class);
        return projectTypeDto;
    }
    
    /*
     * Convert Project to DTO
     */
    private ProjectDto convertProjectToDto(Project project) {
        ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
        return projectDto;
    }
    
    /*
     * Convert ProjectDto to Entity
     */
    private Project convertProjectDtoToEntity(ProjectDto projectDto) {
        Project project = modelMapper.map(projectDto, Project.class);
        return project;
    }

}

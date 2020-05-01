package hmm.architecturestudio.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hmm.architecturestudio.management.model.ProjectType;
import hmm.architecturestudio.management.repository.ProjectTypesRepository;

@Service
public class ProjectsService {

    @Autowired
    private ProjectTypesRepository projectTypesRepository;

    public List<ProjectType> getProjectTypes() {
        return this.projectTypesRepository.findAll();
    }
}

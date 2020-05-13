package hmm.architecturestudio.management.service;

import hmm.architecturestudio.management.model.Role;
import hmm.architecturestudio.management.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    /**
     * This method dont use PrivilegesChecker to check permissions because it is a public service.
     */
    public List<Role> findAll() {
        return this.rolesRepository.findAll();
    }

}

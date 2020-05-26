package hmm.architecturestudio.management.util;

import hmm.architecturestudio.management.model.Privilege;
import hmm.architecturestudio.management.model.ProjectType;
import hmm.architecturestudio.management.model.Role;
import hmm.architecturestudio.management.model.User;
import hmm.architecturestudio.management.repository.PrivilegesRepository;
import hmm.architecturestudio.management.repository.ProjectTypesRepository;
import hmm.architecturestudio.management.repository.RolesRepository;
import hmm.architecturestudio.management.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MyDatabaseSeeder {

    @Autowired
    private PrivilegesRepository privilegesRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProjectTypesRepository projectTypesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void seedDatabase() {
        this.seedPrivilegesTable();
        this.seedRolesTable();
        this.seedRolesPrivilegesRelationTable();
        this.seedUsersTable();
        this.seedProjectTypesTable();
    }

    private void seedPrivilegesTable() {
        Set<Privilege> privileges = Constants.PRIVILEGES_LIST.stream()
                .map(privilegeName -> new Privilege(privilegeName))
                .collect(Collectors.toSet());

        for(Privilege privilege : privileges) {
            Optional<Privilege> p = privilegesRepository.findByName(privilege.getName());
            if (!p.isPresent()) {
                privilegesRepository.save(privilege);
            }
        }
    }

    private void seedRolesTable() {
        Set<Role> roles = Constants.ROLES_LIST.stream()
                .map(roleName -> new Role(roleName))
                .collect(Collectors.toSet());

        for(Role role : roles) {
            Optional<Role> r = rolesRepository.findByName(role.getName());
            if (!r.isPresent()) {
                rolesRepository.save(role);
            }
        }
    }

    private void seedRolesPrivilegesRelationTable() {

        Set<String> roleNames = Constants.PRIVILEGES_BY_ROLE.keySet();
        for(String roleName : roleNames) {
            List<String> privilegesListOfThisRole = Constants.PRIVILEGES_BY_ROLE.get(roleName);

            Optional<Role> role = rolesRepository.findByName(roleName);
            if (role.isPresent()) {
                Set<Privilege> privilegesToSave = new HashSet<>();
                for(String privilegeName : privilegesListOfThisRole) {
                    Optional<Privilege> privilege = privilegesRepository.findByName(privilegeName);
                    if (privilege.isPresent()) {
                        privilegesToSave.add(privilege.get());
                    }
                }
                Role r = role.get();
                r.setPrivileges(privilegesToSave);
                rolesRepository.save(r);
            }
        }
    }

    private void seedUsersTable() {

        Optional<User> optUser = usersRepository.findByUsername(Constants.ADMIN_USERNAME);
        User user;
        if (optUser.isPresent()) {
            user = optUser.get();
        }
        else {
            user = new User();
        }

        // Admin mock data
        user.setUsername(Constants.ADMIN_USERNAME);
        user.setPassword(passwordEncoder.encode("1234"));
        user.setEmail("no-reply@localhost");

        user.setName("Admin");
        user.setLastname("Admin");
        user.setPhone("+34666776677");
        user.setAddress("Fake address");

        Set<Role> roles = new HashSet<>();
        Optional<Role> adminRole = rolesRepository.findByName(Constants.ADMINISTRATOR);

        if (adminRole.isPresent()) {
            roles.add(adminRole.get());
        }

        user.setRoles(roles);

        usersRepository.save(user);
    }

    private void seedProjectTypesTable() {
        Set<ProjectType> projectTypes = Constants.PROJECT_TYPE_LIST.stream()
                .map(projectTypeName -> new ProjectType(projectTypeName))
                .collect(Collectors.toSet());

        for(ProjectType projectType : projectTypes) {
            Optional<ProjectType> pt = projectTypesRepository.findByName(projectType.getName());
            if (!pt.isPresent()) {
                projectTypesRepository.save(projectType);
            }
        }
    }

}


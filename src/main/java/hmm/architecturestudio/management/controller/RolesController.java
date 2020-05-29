package hmm.architecturestudio.management.controller;

import hmm.architecturestudio.management.dto.RoleDto;
import hmm.architecturestudio.management.model.Role;
import hmm.architecturestudio.management.service.RolesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RolesService rolesService;

    /**
     * This endpoint dont catch Privileges Exception, because get role list is a public service.
     */
    @GetMapping
    @ResponseBody
    public List<RoleDto> getRoles() {

        List<Role> roles = null;
        roles = rolesService.findAll();
        return roles.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private RoleDto convertToDto(Role role) {
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
        return roleDto;
    }

}
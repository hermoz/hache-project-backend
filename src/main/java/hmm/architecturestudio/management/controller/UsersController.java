package hmm.architecturestudio.management.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import java.util.Optional;
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

import hmm.architecturestudio.management.dto.UserDto;
import hmm.architecturestudio.management.exception.PrivilegesException;
import hmm.architecturestudio.management.exception.ValidationServiceException;
import hmm.architecturestudio.management.model.User;
import hmm.architecturestudio.management.service.UsersService;

/*
 * Anotation with @RestController because an API REST
 * and mapping the controller with "/users" all REST methods
 * and then each method has its own endpoint
 */
@RestController
@RequestMapping("/api/users")
public class UsersController {

	@Autowired
	private ModelMapper modelMapper;
	/*
	 * Import interface UsersService
	 */
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }
    
    /*
     * ResponseEntity represents the whole HTTP response: status code, headers, and body
     */

    @GetMapping
    @ResponseBody
    public List<UserDto> getUsers() {
        List<User> users = null;
        try {
            users = usersService.findAll();
        } catch (PrivilegesException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }

        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
	/*
	 * Get User by Id
	 */

    @GetMapping(value = "/{id}")
    @ResponseBody
    public UserDto getUser(@PathVariable("id") Long id) {
        Optional<User> user = null;
        try {
            user = usersService.findById(id);
        } catch (PrivilegesException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }

        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }

        return convertToDto(user.get());
    }
    
    /*
     * Create User
     */
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        User user = convertToEntity(userDto);
        User createdUser = null;

        try {
            createdUser = usersService.createUser(user);
        } catch (PrivilegesException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ValidationServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return convertToDto(createdUser);
    }
    
    /*
     * Update User
     */
    
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserDto updateUser(@Valid @RequestBody UserDto userDto) {
        User user = convertToEntity(userDto);
        User createdUser = null;

        try {
            createdUser = usersService.updateUser(user);
        } catch (PrivilegesException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ValidationServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return convertToDto(createdUser);
    }
    
    /*
     * Delete User
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id) {
        try {
            usersService.deleteById(id);
        } catch (PrivilegesException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ValidationServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    
    /*
     * Convert entity to DTO
     */
    private UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }
    
    /*
     * Convert DTO to entity
     */
    private User convertToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }
    


}

package hmm.architecturestudio.management.controller;

import hmm.architecturestudio.management.model.User;
import hmm.architecturestudio.management.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/*
 * Anotation with @RestController because an API REST
 * and mapping the controller with "/users" all REST methods
 * and then each method has its own endpoint
 */
@RestController
@RequestMapping("/users")
public class UsersController {

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
    public ResponseEntity<List<User>> findAll() {
        List<User> allUsers = this.usersService.findAll();
        // Simple case is response with body users and HTTP 200 response
        return ResponseEntity.ok(allUsers);
    }

}

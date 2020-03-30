package hmm.architecturestudio.management.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import hmm.architecturestudio.management.model.User;
import hmm.architecturestudio.management.repository.UsersRepository;

/**
 * This class overrides the method loadUserByUsername so we obtain user´s details from database using the username
 * The Spring Security Authentication Manager calls this method for getting the user details from the database 
 * when authenticating the user details provided by the user
 * @author Herminia
 *
 */
@Component
public class JwtUserDetailsService implements UserDetailsService {

	/**
	 * Inyection of UsersRepository
	 */
	@Autowired
    private UsersRepository usersRepository;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// Obtain user by its username
		/*
		 * This method return UserDetail which is Spring Security User
		 */
        Optional<User> user = usersRepository.findByUsername(username);
        
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        /*
         * Specific implementation where we create the user´s instance and constructor with username, password and arraylist
         * Username and password are obtained from user object user.
         */
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
                new ArrayList<>());
    }
	

}



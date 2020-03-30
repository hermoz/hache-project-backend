package hmm.architecturestudio.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hmm.architecturestudio.management.config.JwtToken;
import hmm.architecturestudio.management.dto.JwtRequest;
import hmm.architecturestudio.management.dto.JwtResponse;
import hmm.architecturestudio.management.service.JwtUserDetailsService;

/**
 * This class validates username and password using Spring Authentication Manager (Spring Security)
 * In case credentials are valid, the token is created and provided to the client
 * @author Herminia
 *
 */
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        
        /**
         * In case AuthenticationManager return true because a valid user
         * We call generateToken (UserDetails) method to generate a valid Token which is going 
         * to be recieved by JwtTokenUtil class
         */
        final String token = jwtToken.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    /**
     * In case credentials are valid a JWT token is created using the JWTTokenUtil
     * In case credentials are not valid:
     * - DisabledException will be thrown if an account is disabled
     * - BadCredentialsException will be thrown if incorrect credentials are presented
     * @param username
     * @param password
     * @throws Exception
     */
    private void authenticate(String username, String password) throws Exception {

        try {
        	// if credentials are valid, a JWT token is created 
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

package hmm.architecturestudio.management.dto;


/**
 * Class requeried for creating a response containing de JWT to be returned to the user
 * @author Herminia
 *
 */

public class JwtResponse {

    private static final long serialVersionUID = -8091879091924046844L;
    
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }

}

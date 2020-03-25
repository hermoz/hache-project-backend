package hmm.architecturestudio.management.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * With this class we reject every unauthenticated request and send error code 401
 * Extends  from Spring’s AuthenticationEntryPoint class 
 * @author Herminia
 *
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, 
			AuthenticationException authException) throws IOException, ServletException {
		/**
		 * When accessing the app without authentication this entry point kicks in and throws a 401 statys code
		 */
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}

}

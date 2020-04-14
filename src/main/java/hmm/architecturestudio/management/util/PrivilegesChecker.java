package hmm.architecturestudio.management.util;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

public class PrivilegesChecker {

	public boolean hasPrivilege(String privilegeName, Collection<? extends GrantedAuthority> authorities) {
		/**
		 * We package on a list every mapping we do on each iteration 
		 */
        return authorities.stream().map(authority -> authority.getAuthority()).collect(Collectors.toList()).contains(privilegeName);
    }
}

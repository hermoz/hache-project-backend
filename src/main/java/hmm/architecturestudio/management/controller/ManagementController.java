package hmm.architecturestudio.management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlar creado para prueba de autenticación con JWT
 * @author Herminia
 *
 */
@RestController
public class ManagementController {
	
	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello. My Management Applicaction is working";
	}

}

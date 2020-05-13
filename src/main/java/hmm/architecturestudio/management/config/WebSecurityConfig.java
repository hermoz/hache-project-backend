package hmm.architecturestudio.management.config;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import hmm.architecturestudio.management.service.JwtUserDetailsService;
/**
 * Configuration class which includes the
 * Definition of our own custom configuration
 * Annotations below are used to substitute auto-config
 * @author Herminia
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Configure a global instance of AuthenticationManagerBuilder
     * 
     * Configure AuthenticationManager so it knows from where to load the user for matching credentials
     * 
     * Declared with the @Autowired annotation so that Spring will inject the object of
     * the AuthenticationManagerBuilder
     * 
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPointBean() throws Exception {
        return new JwtAuthenticationEntryPoint();
    }


    /**
     * Password is encripted so we use Bcrypt
     * BCryptPasswordEncoder is an encoder class used to encode the password. 
     * It uses the bcrpt algorithm
     */
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    

    /**
     * Bean neccesary to perform the entity-DTO conversion
     * @return
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Configuration of rest api url to be passed as authorized request
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and()// enable CORS to dont get cors exceptions from clients
                .authorizeRequests()
                .antMatchers("/api/auth/authenticate").permitAll() // this endpoint permit like not authenticathed
                .anyRequest().authenticated().and() // all others requests must be authenticated
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and() // exception handling delegated to jwtAuthenticationEntryPoint
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // No session will be created by Spring Security
                .csrf().disable(); // disable csrf

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    // We declare this bean to configure CORS (origins, methods, headers, etc)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        // We allow all origins with the wildcard *
        configuration.setAllowedOrigins(List.of("*"));
        // We only use GET, POST, PUT ,DETE but we allow all common verb methods
        configuration.setAllowedMethods(
                List.of("HEAD","GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        );
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("X-Auth-Token","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    

}

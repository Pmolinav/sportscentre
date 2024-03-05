package net.pmolinav.configuration.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import net.pmolinav.configuration.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    private final UserClient userClient;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    public WebSecurityConfig(UserClient userClient,
                             JWTAuthorizationFilter jwtAuthorizationFilter) {
        super();

        this.userClient = userClient;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;

        // Inherit security context in async function calls
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    /**
     * Method used to configure the authentication manager with the correct provider.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username ->
                new UserDetailsServiceImpl().loadUserByUsername(username));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
//                            logger.error("Unauthorized request - {}", ex.getMessage());
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                        }
                )
                .and();

        // Set permissions on endpoints
        http.authorizeRequests()
                // Swagger endpoints must be publicly accessible
                .antMatchers("/*").permitAll()
                .antMatchers("/api-docs").permitAll()
                .antMatchers("/csrf").permitAll()
                .antMatchers("/api/swagger-ui/**").permitAll()
                .antMatchers("/api/swagger.html").permitAll()
                .antMatchers("/api-docs/swagger-config").permitAll()
                // Our public endpoints
//                .antMatchers("/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/author/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/author/search").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/book/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/book/search").permitAll()
                // Our private endpoints
                .anyRequest().authenticated();

        // Add JWT token filter
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Used by spring security if CORS is enabled.
     *
     * @return CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * Method used to manage authentication.
     *
     * @return AuthenticationManager
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Method used to configure Swagger authorization.
     *
     * @return OpenApi
     */
    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "BearerToken";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

    /**
     * Method used to return a specific password encoder. BCrypt encryption is used in this API.
     *
     * @return PasswordEncoder
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

package com.rentar.rentar;
import com.rentar.rentar.servicios.ClienteUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${app.security.admin.username}")
    private String adminUsername;

    @Value("${app.security.admin.password}")
    private String adminPassword;

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder,
            ClienteUserDetailsService clienteUserDetailsService) {

        UserDetails administrador = User.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .roles("ADMIN")
                .build();

        InMemoryUserDetailsManager administradorEnMemoria =
                new InMemoryUserDetailsManager(administrador);

        return username -> {
            if (adminUsername.equals(username)) {
                return administradorEnMemoria.loadUserByUsername(
                        adminUsername);
            }

            return clienteUserDetailsService.loadClientByEmail(username);
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/graphiql",
                    "/graphiql/**"
                ).permitAll()
                .requestMatchers(
                    "/api/vehiculos/**",
                    "/api/clientes/**"
                ).hasRole("ADMIN")
                .requestMatchers("/api/reservas/**")
                    .hasAnyRole("CLIENT", "ADMIN")
                .requestMatchers("/graphql")
                    .authenticated()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}

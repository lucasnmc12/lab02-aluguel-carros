package br.edu.pucminas.aluguel.config;

import br.edu.pucminas.aluguel.repository.ClienteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SegurancaConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/entrar", "/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/clientes/novo").permitAll()
                        .requestMatchers(HttpMethod.POST, "/clientes").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/entrar")
                        .usernameParameter("cpf")
                        .defaultSuccessUrl("/pedidos", true)
                        .failureUrl("/entrar?erro")
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/sair")
                        .logoutSuccessUrl("/entrar?saiu"))
                .build();
    }

    @Bean
    UserDetailsService userDetailsService(ClienteRepository repository) {
        return cpf -> repository.findByCpf(cpf.replaceAll("\\D", ""))
                .map(cliente -> User.withUsername(cliente.getCpf())
                        .password(cliente.getSenhaHash())
                        .roles("CLIENTE").build())
                .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado"));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

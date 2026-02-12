package com.example.saudejapostosaudeservice.infrastructure.input.api.configs;

import com.example.saudejapostosaudeservice.infrastructure.input.api.security.CustomAccessDeniedHandler;
import com.example.saudejapostosaudeservice.infrastructure.input.api.security.CustomAuthenticationEntryPoint;
import enums.TipoUsuarioEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("api")
public class ApiConfig {

    private static final String[] WHITE_LIST_URL = { "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs",
            "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/api/auth/**",
            "/api/test/**" };

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(WHITE_LIST_URL).permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/solicitacoes-vinculo-paciente-posto-saude/*").hasAuthority(TipoUsuarioEnum.PACIENTE.name())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/solicitacoes-vinculo-paciente-posto-saude/*").hasAuthority(TipoUsuarioEnum.AGENTE_COMUNITARIO.name())
                                .requestMatchers(HttpMethod.POST, "/api/v1/postos-saude").hasAuthority(TipoUsuarioEnum.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/postos-saude/*").hasAuthority(TipoUsuarioEnum.GERENTE.name())
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/postos-saude/*").hasAuthority(TipoUsuarioEnum.GERENTE.name())
                                .requestMatchers(HttpMethod.GET, "/api/v1/postos-saude/pacientes/*").hasAnyAuthority(TipoUsuarioEnum.MEDICO.name(), TipoUsuarioEnum.ENFERMEIRO.name(), TipoUsuarioEnum.AGENTE_COMUNITARIO.name(), TipoUsuarioEnum.GERENTE.name())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/postos-saude/pacientes/remover/**").hasAuthority(TipoUsuarioEnum.AGENTE_COMUNITARIO.name())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/postos-saude/profissionais-saude/vincular/**").hasAuthority(TipoUsuarioEnum.GERENTE.name())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/postos-saude/profissionais-saude/remover/**").hasAuthority(TipoUsuarioEnum.GERENTE.name())
                                .anyRequest().authenticated())

                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))

                .oauth2ResourceServer(
                        conf -> conf.jwt(Customizer.withDefaults()));

        return http.build();
    }

}

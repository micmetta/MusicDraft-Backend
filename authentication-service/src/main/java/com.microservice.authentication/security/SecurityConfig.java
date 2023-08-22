package com.microservice.authentication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;


// La classe SecurityConfig è annotata con @Configuration, indicando che è una classe di configurazione per Spring.
// La classe SecurityConfig è annotata anche con @EnableWebSecurity, indicando che abilita la sicurezza basata sul web in Spring Security.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Il metodo securityFilterChain è annotato con @Bean, che restituisce una catena di filtri di sicurezza per gestire le richieste HTTP.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Configurazione del filtro di sicurezza per le richieste HTTP (Il filtro di sicurezza viene configurato tramite il metodo http di tipo HttpSecurity.)
        //noinspection removal
        return http
                .csrf(csrf -> csrf.disable()) // Il metodo csrf disabilita la protezione CSRF, che è una misura di sicurezza per prevenire attacchi di cross-site request forgery. (L'ho dovuto inserire altrimenti avevo problemi con la POST..)
                .headers(headers -> headers.cacheControl().disable()) // Disabilita la memorizzazione nella cache
                .authorizeHttpRequests( authorizationManagerRequestMatcherRegistry -> {

                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers(HttpMethod.POST, "/api/registration").permitAll() // Permette richieste POST a "/registration" senza autenticazione.
                            .requestMatchers(HttpMethod.GET, "/api/getAllUsers").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/loginRegistered/{nickname}/{password}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/findByNickname/{nickname}").permitAll() // Permette richieste GET a "/loginRegistered" senza autenticazione.
                            .requestMatchers(HttpMethod.GET, "/api/getEmail/{nickname}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/getNickname/{email}").permitAll();

                    authorizationManagerRequestMatcherRegistry.anyRequest().authenticated(); // Richiede l'autenticazione per tutte le altre richieste (ci deve essere altrimenti se faccio la GET dopo che viene scelto l'account Google mi da errore 403).
                })
                .oauth2Login(Customizer.withDefaults()) // Abilita il login OAuth2 con impostazioni predefinite (andrà a vedere nell'application.proprieties il cliet-id e il client-secret per permettere l'autenticazione con GOOGLE)
                .build(); // La catena di filtri di sicurezza viene costruita utilizzando il metodo build().

    }

//    private static class CorsConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            http.cors();
//        }
//    }


}

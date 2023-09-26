package com.microservice.matchmaking.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Il metodo securityFilterChain è annotato con @Bean,
    // che restituisce una catena di filtri di sicurezza per gestire le richieste HTTP.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Configurazione del filtro di sicurezza per le richieste HTTP (Il filtro di sicurezza viene configurato tramite il metodo http di tipo HttpSecurity.)
        //noinspection removal
        return http
                .csrf(csrf -> csrf.disable()) // Il metodo csrf disabilita la protezione CSRF, che è una misura di sicurezza per prevenire attacchi di cross-site request forgery. (L'ho dovuto inserire altrimenti avevo problemi con la POST..)
                .headers(headers -> headers.cacheControl().disable()) // Disabilita la memorizzazione nella cache
                .authorizeHttpRequests( authorizationManagerRequestMatcherRegistry -> {

                    authorizationManagerRequestMatcherRegistry

                            // Endpoints di MatchmakingController:
                            .requestMatchers(HttpMethod.POST, "/api/v1/matchmakingService/matchmakingController/CreaNuovaPartita").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/matchmakingService/matchmakingController/CancellaPartita/{id}").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/v1/matchmakingService/matchmakingController/CercaMatch").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/matchmakingService/matchmakingController/getAllPartiteSenzaMatch").permitAll()

                            // Endpoints di RiepilogoPartiteConcluseController:
                            .requestMatchers(HttpMethod.POST, "/api/v1/matchmakingService/riepilogoPartitaConclusaController/addPartitaConclusa").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/matchmakingService/riepilogoPartitaConclusaController/getAllPartiteConcluse/{nickname}").permitAll();



                    authorizationManagerRequestMatcherRegistry.anyRequest().authenticated(); // Richiede l'autenticazione per tutte le altre richieste (ci deve essere altrimenti se faccio la GET dopo che viene scelto l'account Google mi da errore 403).
                })
                .oauth2Login(Customizer.withDefaults()) // Abilita il login OAuth2 con impostazioni predefinite (andrà a vedere nell'application.proprieties il cliet-id e il client-secret per permettere l'autenticazione con GOOGLE)
                .build(); // La catena di filtri di sicurezza viene costruita utilizzando il metodo build().
    }
}

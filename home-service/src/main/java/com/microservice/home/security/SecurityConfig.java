package com.microservice.home.security;


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

    //Il metodo securityFilterChain è annotato con @Bean, che restituisce una catena di filtri di sicurezza per gestire le richieste HTTP.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Configurazione del filtro di sicurezza per le richieste HTTP (Il filtro di sicurezza viene configurato tramite il metodo http di tipo HttpSecurity.)
        //noinspection removal
        return http
                .csrf(csrf -> csrf.disable()) // Il metodo csrf disabilita la protezione CSRF, che è una misura di sicurezza per prevenire attacchi di cross-site request forgery. (L'ho dovuto inserire altrimenti avevo problemi con la POST..)
                .headers(headers -> headers.cacheControl().disable()) // Disabilita la memorizzazione nella cache
                .authorizeHttpRequests( authorizationManagerRequestMatcherRegistry -> {

                    authorizationManagerRequestMatcherRegistry
                            // Endpoints di HomeController:
                            .requestMatchers(HttpMethod.POST, "/api/v1/homeService/addAmico/{nickameU1}/{nicknameU2}").permitAll() // Permette richieste POST a "/registration" senza autenticazione.
                            .requestMatchers(HttpMethod.GET, "/api/v1/homeService/richiesteInAttesa/{nickname}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/homeService/richiesteInAttesaUtenti/{nickname}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/homeService/getAllFriends/{nickname}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/homeService/getAllFriendsAreOnline/{nickname}").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/v1/homeService/richiestaAccettata/{nicknameU1}/{nicknameU2}").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/homeService/richiestaRifiutata/{nicknameU1}/{nicknameU2}").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/homeService/cancellaAmicizia/{nicknameU1}/{nicknameU2}").permitAll()

                            // Endopoints di ScambiController:
                            .requestMatchers(HttpMethod.POST, "/api/v1/homeService/scambiController/inviaOfferta").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/homeService/scambiController/cancellaOffertaTramiteId/{id}").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/homeService/scambiController/cancellaOffertaTramiteIdStart/{idStart}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/homeService/scambiController/getAllOfferte/{nickname}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/homeService/scambiController/getAllOfferteInviate/{nickname}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/homeService/scambiController/getStorico/{id}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/homeService/scambiController/getUltimaControfferta/{id}").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/v1/homeService/scambiController/controfferta").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/v1/homeService/scambiController/accettaOfferta/{id}").permitAll();

                    authorizationManagerRequestMatcherRegistry.anyRequest().authenticated(); // Richiede l'autenticazione per tutte le altre richieste (ci deve essere altrimenti se faccio la GET dopo che viene scelto l'account Google mi da errore 403).
                })
                .oauth2Login(Customizer.withDefaults()) // Abilita il login OAuth2 con impostazioni predefinite (andrà a vedere nell'application.proprieties il cliet-id e il client-secret per permettere l'autenticazione con GOOGLE)
                .build(); // La catena di filtri di sicurezza viene costruita utilizzando il metodo build().
    }
}

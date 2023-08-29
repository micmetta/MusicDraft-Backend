package com.example.collectdata.model;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.net.URI;

public class Auth {

    private String clientId;


    private String redirectUri;


    private String clientSecret;

    private SpotifyApi spotifyApi;



    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public SpotifyApi getSpotifyApi() {
        return spotifyApi;
    }



    public Auth() {
        this.clientId="7a2322fa3c804f7e9e4c7b48f9bf8ca0";
        this.clientSecret="1c19f17742b44836a36295661a4ee607";
        this.redirectUri="https://localhost:3000";
        this.spotifyApi=new SpotifyApi.Builder()
                .setClientId(this.clientId)
                .setClientSecret(this.clientSecret)
                .setRedirectUri(URI.create(this.redirectUri))
                .build();


    }
    public void clientCredentials_Sync() {
        ClientCredentialsRequest clientCredentialsRequest = this.spotifyApi.clientCredentials()
                .build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            this.spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException |  org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

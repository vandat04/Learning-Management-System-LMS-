package com.lms.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.lms.util.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GoogleTokenVerifier {

    @Value("${google.client-id}")
    private String clientId;

    private final Validate validate;

    public GoogleIdToken.Payload verify(String idTokenString) {

        try {
            GoogleIdTokenVerifier verifier =
                    new GoogleIdTokenVerifier.Builder(
                            new NetHttpTransport(),
                            new JacksonFactory()
                    )
                            .setAudience(List.of(clientId))
                            .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) {
                validate.saveError(4);
                return null;
            }

            return idToken.getPayload();

        } catch (Exception e) {
            validate.saveError(4);
            return null;
        }
    }
}
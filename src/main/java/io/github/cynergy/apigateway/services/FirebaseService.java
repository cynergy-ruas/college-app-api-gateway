package io.github.cynergy.apigateway.services;

import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import org.springframework.stereotype.Component;

@Component
public class FirebaseService {
    public FirebaseService() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.getApplicationDefault())
                .build();

        FirebaseApp.initializeApp(options);
    }

    public VerificationResult verifyIdToken(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken, true);
            return new VerificationResult(true, decodedToken.getUid());
        } catch (FirebaseAuthException e) {
            return new VerificationResult(false);
        } catch (IllegalArgumentException e) {
            return new VerificationResult(false);
        }
    }
}

class VerificationResult {
    boolean valid;
    String uid;

    VerificationResult(boolean valid) {
        this.valid = valid;
    }

    VerificationResult(boolean valid, String uid) {
        this(valid);
        this.uid = uid;
    }

    public boolean isValid() {
        return valid;
    }

    public String getUid() {
        return uid;
    }
}

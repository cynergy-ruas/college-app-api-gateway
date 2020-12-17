package io.github.cynergy.apigateway.services;

import java.util.Map;

public class VerificationResult {
    boolean valid;
    String uid;
    Map<String, Object> claims;

    VerificationResult(boolean valid) {
        this.valid = valid;
    }

    VerificationResult(boolean valid, String uid, Map<String, Object> claims) {
        this(valid);
        this.uid = uid;
        this.claims = claims;
    }

    public boolean isValid() {
        return valid;
    }

    public String getUid() {
        return uid;
    }

    public Map<String, Object> getClaims() {
        return claims;
    }
}

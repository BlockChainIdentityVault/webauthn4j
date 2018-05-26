package sample;

import com.webauthn4j.WebAuthnAuthenticationContext;
import com.webauthn4j.WebAuthnRegistrationContext;
import com.webauthn4j.authenticator.Authenticator;
import com.webauthn4j.client.Origin;
import com.webauthn4j.client.challenge.Challenge;
import com.webauthn4j.client.challenge.DefaultChallenge;
import com.webauthn4j.server.ServerProperty;
import com.webauthn4j.test.TestUtil;
import com.webauthn4j.test.platform.AttestationConveyancePreference;
import com.webauthn4j.test.platform.AuthenticatorAttestationResponse;
import com.webauthn4j.test.platform.PublicKeyCredentialCreationOptions;
import com.webauthn4j.test.platform.PublicKeyCredentialRpEntity;
import com.webauthn4j.validator.WebAuthnAuthenticationContextValidator;
import com.webauthn4j.validator.WebAuthnRegistrationContextValidator;

public class Sample {

    public void registrationValidationSample(){
        // Client properties
        byte[] collectedClientData = null /* set collectedClientData */;
        byte[] attestationObject   = null /* set attestationObject */;

        // Server properties
        Origin origin          = null /* set origin */;
        String rpId            = null /* set rpId */;
        Challenge challenge    = null /* set challenge */;
        byte[] tokenBindingId  = null /* set tokenBindingId */;
        ServerProperty serverProperty = new ServerProperty(origin, rpId, challenge, tokenBindingId);

        WebAuthnRegistrationContext registrationContext = new WebAuthnRegistrationContext(collectedClientData, attestationObject, serverProperty);

        WebAuthnRegistrationContextValidator webAuthnRegistrationContextValidator =
                WebAuthnRegistrationContextValidator.createNullAttestationStatementValidator();

        webAuthnRegistrationContextValidator.validate(registrationContext);
    }

    public void authenticationValidationSample(){
        // Client properties
        byte[] credentialId        = null /* set credentialId */;
        byte[] collectedClientData = null /* set collectedClientData */;
        byte[] authenticatorData = null /* set authenticatorData */;
        byte[] signature = null /* set signature */;

        // Server properties
        Origin origin          = null /* set origin */;
        String rpId            = null /* set rpId */;
        Challenge challenge    = null /* set challenge */;
        byte[] tokenBindingId  = null /* set tokenBindingId */;
        ServerProperty serverProperty = new ServerProperty(origin, rpId, challenge, tokenBindingId);

        WebAuthnAuthenticationContext authenticationContext =
                new WebAuthnAuthenticationContext(
                        credentialId,
                        collectedClientData,
                        authenticatorData,
                        signature,
                        serverProperty
                );
        Authenticator authenticator = null /* set authenticator */;

        WebAuthnAuthenticationContextValidator webAuthnAuthenticationContextValidator =
                new WebAuthnAuthenticationContextValidator();

        webAuthnAuthenticationContextValidator.validate(authenticationContext, authenticator, true);
    }
}
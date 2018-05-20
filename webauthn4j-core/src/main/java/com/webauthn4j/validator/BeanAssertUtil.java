package com.webauthn4j.validator;

import com.webauthn4j.WebAuthnAuthenticationContext;
import com.webauthn4j.WebAuthnRegistrationContext;
import com.webauthn4j.attestation.AttestationObject;
import com.webauthn4j.attestation.authenticator.AttestedCredentialData;
import com.webauthn4j.attestation.authenticator.AuthenticatorData;
import com.webauthn4j.attestation.authenticator.CredentialPublicKey;
import com.webauthn4j.attestation.authenticator.extension.Extension;
import com.webauthn4j.attestation.statement.AttestationStatement;
import com.webauthn4j.client.CollectedClientData;
import com.webauthn4j.client.TokenBinding;
import com.webauthn4j.rp.RelyingParty;
import com.webauthn4j.util.UnsignedNumberUtil;
import com.webauthn4j.validator.exception.BadRpIdException;
import com.webauthn4j.validator.exception.ConstraintViolationException;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class BeanAssertUtil {

    private BeanAssertUtil() {
    }

    public static void validate(WebAuthnAuthenticationContext webAuthnAuthenticationContext) {
        if (webAuthnAuthenticationContext.getCredentialId() == null) {
            throw new ConstraintViolationException("credentialId must not be null");
        }
        if (webAuthnAuthenticationContext.getCollectedClientData() == null) {
            throw new ConstraintViolationException("collectedClientData must not be null");
        }
        if (webAuthnAuthenticationContext.getAuthenticatorData() == null) {
            throw new ConstraintViolationException("authenticatorData must not be null");
        }
        if (webAuthnAuthenticationContext.getSignature() == null) {
            throw new ConstraintViolationException("signature must not be null");
        }
        if (webAuthnAuthenticationContext.getRelyingParty() == null) {
            throw new ConstraintViolationException("relyingParty must not be null");
        }
    }

    public static void validate(WebAuthnRegistrationContext webAuthnRegistrationContext) {
        if (webAuthnRegistrationContext.getAttestationObject() == null) {
            throw new ConstraintViolationException("attestationObject must not be null");
        }
        if (webAuthnRegistrationContext.getCollectedClientData() == null) {
            throw new ConstraintViolationException("collectedClientData must not be null");
        }
        if (webAuthnRegistrationContext.getRelyingParty() == null) {
            throw new ConstraintViolationException("relyingParty must not be null");
        }
    }

    public static void validate(CollectedClientData collectedClientData) {
        if (collectedClientData.getType() == null) {
            throw new ConstraintViolationException("type must not be null");
        }
        if (collectedClientData.getChallenge() == null) {
            throw new ConstraintViolationException("challenge must not be null");
        }
        if (collectedClientData.getOrigin() == null) {
            throw new ConstraintViolationException("origin must not be null");
        }
        if (collectedClientData.getTokenBinding() != null) {
            validate(collectedClientData.getTokenBinding());
        }
    }

    public static void validate(TokenBinding tokenBinding) {
        if (tokenBinding.getStatus() == null) {
            throw new ConstraintViolationException("status must not be null");
        }
    }

    public static void validate(AttestationObject attestationObject) {
        if (attestationObject.getAttestationStatement() == null) {
            throw new ConstraintViolationException("attestationStatement must not be null");
        }
        if (attestationObject.getAuthenticatorData() == null) {
            throw new ConstraintViolationException("authenticatorData must not be null");
        }
    }

    public static void validate(AuthenticatorData authenticatorData) {

        // attestedCredentialData may be null
        AttestedCredentialData attestedCredentialData = authenticatorData.getAttestedCredentialData();
        if (attestedCredentialData != null) {
            validate(attestedCredentialData);
        }

        byte[] rpIdHash = authenticatorData.getRpIdHash();
        if (rpIdHash == null) {
            throw new ConstraintViolationException("rpIdHash must not be null");
        }
        if (rpIdHash.length != 32) {
            throw new BadRpIdException("rpIdHash must be 32 bytes length");
        }

        long signCount = authenticatorData.getSignCount();
        if (signCount <= 0 || signCount > UnsignedNumberUtil.UNSIGNED_INT_MAX) {
            throw new ConstraintViolationException("signCount must be unsigned int");
        }
        List<Extension> extensions = authenticatorData.getExtensions();
        if (extensions == null) {
            throw new ConstraintViolationException("extensions must not be null");
        }
        extensions.forEach(BeanAssertUtil::validate);

    }

    private static void validate(AttestedCredentialData attestedCredentialData) {

        byte[] aaGuid = attestedCredentialData.getAaGuid();
        if (aaGuid == null) {
            throw new ConstraintViolationException("aaGuid must not be null");
        }
        if (aaGuid.length != 16) {
            throw new ConstraintViolationException("aaGuid must not be 16 bytes length");
        }

        if (attestedCredentialData.getCredentialId() == null) {
            throw new ConstraintViolationException("credentialId must not be null");
        }

        CredentialPublicKey credentialPublicKey = attestedCredentialData.getCredentialPublicKey();
        if (credentialPublicKey == null) {
            throw new ConstraintViolationException("credentialPublicKey must not be null");
        }
        validate(credentialPublicKey);
    }

    private static void validate(CredentialPublicKey credentialPublicKey) {
        credentialPublicKey.validate();
    }

    private static void validate(Extension extension) {
        // nop for now
    }

    public static void validate(RelyingParty relyingParty) {
        if (relyingParty.getRpId() == null) {
            throw new ConstraintViolationException("rpId must not be null");
        }
        if (relyingParty.getChallenge() == null) {
            throw new ConstraintViolationException("challenge must not be null");
        }
        if (relyingParty.getOrigin() == null) {
            throw new ConstraintViolationException("origin must not be null");
        }
    }

    public static void validate(AttestationStatement attestationStatement) {
        attestationStatement.validate();
    }
}
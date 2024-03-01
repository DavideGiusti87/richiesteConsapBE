package com.proggettazione.richiesteConsapBE.configuration;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.text.ParseException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class JwtUtil {
    private static final int ORE_SCADENZA_TOKEN = 24;
    private static final int ORE_SCADENZA_AGGIORNAMENTO_TOKEN = 72;
    private static final String SEGRETO = "76E9864C6E64027511F7993BA2DA22EAD359D4B8415D7E7FA6EA4C3AA99D9998";

    //crea un token con payload contenente:
    public static String createAccessToken(String email, String emettitore, List<String> ruoli) {
        try {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(email)
                    .issuer(emettitore) //issuer (chi ha richiesto il JWT)
                    .claim("ruoli", ruoli)
                    .expirationTime(Date.from(Instant.now().plusSeconds(ORE_SCADENZA_TOKEN * 3600))) //expirationTime impostato a 24 ore
                    .issueTime(new Date()) // issueTime, cioè quando è stato creato il token
                    .build();


            Payload payload = new Payload(claims.toJSONObject());

            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256),
                    payload);

            jwsObject.sign(new MACSigner(SEGRETO));
            return jwsObject.serialize();
        }
        catch (JOSEException e) {
            throw new RuntimeException("Errore durante la creazione del JWT", e);
        }
    }

    public static String createRefreshToken(String username) {
        try {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(username)
                    .expirationTime(Date.from(Instant.now().plusSeconds(ORE_SCADENZA_AGGIORNAMENTO_TOKEN * 3600)))
                    .build();

            Payload payload = new Payload(claims.toJSONObject());

            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256),
                    payload);

            jwsObject.sign(new MACSigner(SEGRETO));
            return jwsObject.serialize();
        }
        catch (JOSEException e) {
            throw new RuntimeException("Errore durante la creazione del JWT", e);
        }
    }

    public static UsernamePasswordAuthenticationToken parseToken(String token) throws JOSEException, ParseException,
            BadJOSEException {

        byte[] chiave = SEGRETO.getBytes();
        SignedJWT signedJWT = SignedJWT.parse(token);
        signedJWT.verify(new MACVerifier(chiave));
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();

        JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(JWSAlgorithm.HS256,
                new ImmutableSecret<>(chiave));
        jwtProcessor.setJWSKeySelector(keySelector);
        jwtProcessor.process(signedJWT, null);
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        String username = claimsSet.getSubject();
        /*var ruoli = (List<String>) claimsSet.getClaim("ruoli");
        var authorities = ruoli == null ? null : ruoli.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());*/
        return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()/*, authorities*/);
    }
}

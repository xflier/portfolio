package home.xflier.authn.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import home.xflier.authn.dto.out.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

/**
 * JwtService
 * 
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */

@Service
public class JwtService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Value("${jwt.algorithm}")
    private String jwtAlgorithm;

    @Value("${jwt.key-size}")
    private String jwtKeySize;

    private String secretKey;
    private KeyPair keyPair;

    @PostConstruct
    public void init() {
        String alg = jwtAlgorithm;
        int keySize = Integer.parseInt(jwtKeySize);
        LOGGER.info("algorithm : " + alg);
        LOGGER.info("keySize : " + keySize);
        if ("HmacSHA256".equals(alg)) {
            try {
                KeyGenerator keyGen = KeyGenerator.getInstance(alg);
                keyGen.init(keySize);
                SecretKey key = keyGen.generateKey();
                LOGGER.info("SecretKey = " + bytesToHex(key.getEncoded()));
                LOGGER.info("SecretKey Encoded = " + Base64.getEncoder().encodeToString(key.getEncoded()));
                this.secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
            } catch (NoSuchAlgorithmException e) {
                LOGGER.error("not recognized jwt.algorithm : " + alg);
                System.exit(1);
            }
        }

        if ("RSA256".equals(alg)) {
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(keySize);
                this.keyPair = keyPairGenerator.generateKeyPair();
            } catch (NoSuchAlgorithmException e) {
                LOGGER.error("not recognized jwt.algorithm : " + alg);
                System.exit(1);
            }
        }
    }

    public String generateToken(String userName) {

        // define the claim / payload
        Map<String, Object> claims = new HashMap<>();

        JwtBuilder builder = Jwts.builder();

        LOGGER.info("retrieving user principal ...");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal user = null;
        if (principal instanceof UserPrincipal) {
            user = (UserPrincipal) principal;
            LOGGER.info("user principal = " + user.toString());
        } else {
            LOGGER.info("principal is not a UserPrincipal : " + principal.getClass());
            throw new RuntimeException("principal is not a UserPrincipal");
        }

        LOGGER.info("Generating JWT Token for user login = " + user.toString());

        claims.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        builder.claims(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 3 * 60 * 1000));

        if ("HmacSHA256".equals(jwtAlgorithm)) {
            builder.signWith(getKey());
        }

        if ("RSA256".equals(jwtAlgorithm)) {
            builder.signWith(keyPair.getPrivate());
        }

        return builder.compact();
    }

    public UserPrincipal validateToken(String token) {
        String user = null;
        Claims claims = null;
        try {
            claims = extractAllClaims(token);
            user = claims.getSubject();
        } catch (Exception e) {
            LOGGER.error("Token verification failed : " + e.getMessage());
        }
        
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setUsername(user);
        Collection<? extends GrantedAuthority> authorities = ((List<?>)claims.get("roles")).stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList())  ;
        userPrincipal.setAuthorities(authorities);

        LOGGER.info("UserPrincipal = " + userPrincipal.toString());
        return userPrincipal;
    }

    private SecretKey getKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        // LOGGER.info("bytes[] are : " + bytesToHex(bytes));
        SecretKey key = Keys.hmacShaKeyFor(bytes);
        LOGGER.info("final key : " + bytesToHex(key.getEncoded()));
        return key;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private Claims extractAllClaims(String token) {

        LOGGER.info("invoking extractAllClaims ...");
        Jws<Claims> jws = null;

        if ("HmacSHA256".equals(jwtAlgorithm)) {
            jws = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
        }

        if ("RSA256".equals(jwtAlgorithm)) {
            jws = Jwts.parser().verifyWith(keyPair.getPublic()).build().parseSignedClaims(token);
        }

        if (jws != null) {
            Claims claims = jws.getPayload();
            LOGGER.info("Token header = " + jws.getHeader().toString());

            claims.forEach((k, v) -> {
                LOGGER.info("claim " + k + " = " + v.toString());
            });

            LOGGER.info("Token Digest = " + jws.getDigest());
            return claims;
        } else
            return null;
    }

    // @Autowired
    // public void setUserService(UserService userService) {
    //     this.userService = userService;
    //     init();
    // }

}

package com.example.common.jwt;

import com.example.common.config.CommonProperties;
import com.example.common.context.ExampleContextData;
import com.example.common.enums.ContextDataFieldEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtService {

    private static final String SUBJECT_CLAIM = "sub";

    private final CommonProperties commonProperties;
    private final JwtUserDetailsService jwtUserDetailsService;

    public JwtService(final CommonProperties commonProperties, final JwtUserDetailsService jwtUserDetailsService) {
        this.commonProperties = commonProperties;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    public ExampleContextData buildExampleContextData(String authorizationHeader){
        String jwtToken = getBearerToken(authorizationHeader);
        Claims claims = validateToken(jwtToken);
        authenticate(claims);
        return buildExampleContextData(claims, jwtToken);
    }

    private Claims validateToken(String jwtToken) {
        String secret = commonProperties.getJwtSecret();
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private void authenticate(Claims claims) {
        String userName = getUserName(claims);
        JwtUser user = jwtUserDetailsService.loadUserByUsername(userName);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private ExampleContextData buildExampleContextData(Claims claims, String jwtToken){
        String userName = getUserName(claims);
        return ExampleContextData.builder().jwtToken(jwtToken).userName(userName).build();
    }

    private String getBearerToken(String header) {
        if (header != null && header.startsWith(ContextDataFieldEnum.BEARER.getName())) {
            return header.replace(ContextDataFieldEnum.BEARER.getName(), "").trim();
        }
        return null;
    }

    private String getUserName (Claims claims){
        return (String) claims.get(SUBJECT_CLAIM);
    }

}

package com.example.common.filter;

import com.example.common.context.ContextService;
import com.example.common.context.ExampleContextData;
import com.example.common.jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class CommonServletFilter extends OncePerRequestFilter {

    public static final String URI_AUTH = "/auth";

    private final ContextService contextService;
    private final JwtService jwtService;

    public CommonServletFilter(final ContextService contextService, final JwtService jwtService) {
        this.contextService = contextService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain){
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            ExampleContextData exampleContextData = jwtService.authenticate(authorizationHeader);
            contextService.addContextData(exampleContextData);
            chain.doFilter(request, response);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if(contextService != null) {
                contextService.removeContextData();
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals(URI_AUTH);
    }

}

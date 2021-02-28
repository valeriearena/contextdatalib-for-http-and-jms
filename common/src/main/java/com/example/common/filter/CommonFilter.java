package com.example.common.filter;

import com.example.common.context.ContextService;
import com.example.common.context.ExampleContextData;
import com.example.common.enums.ContextDataFieldEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class CommonFilter extends OncePerRequestFilter {

    private final ContextService contextService;

    public CommonFilter(final ContextService contextService) {
        this.contextService = contextService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain){
        try {
            String userName = request.getHeader(ContextDataFieldEnum.USER_NAME_HEADER.getName());
            ExampleContextData exampleContextData = ExampleContextData.builder().userName(userName).build();
            contextService.buildContextData(exampleContextData);
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

}

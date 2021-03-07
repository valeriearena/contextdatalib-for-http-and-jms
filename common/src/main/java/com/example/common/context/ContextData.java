package com.example.common.context;

import org.springframework.stereotype.Component;

/**
 * ContextData uses ThreadLocal to bind context details (in ExampleContextData) to the thread of each incoming message.
 * Using ThreadLocal supports both HTTP request threads and JMS threads.
 *
 * NOTE: ThreadLocal is used by Spring's RequestContextHolder to provide request scope to beans annotated with @RequestScope.
 *
 * IMPORTANT NOTE: When binding ExampleContextData to ThreadLocal, ExampleContextData MUST be removed from ThreadLocal
 * when the thread exits to avoid memory leaks.
 */
@Component
public class ContextData {

    private static final ThreadLocal<ExampleContextData> CONTEXT_DATA_THREAD_LOCAL = new ThreadLocal<>();

    public void set(ExampleContextData exampleContextData){
        CONTEXT_DATA_THREAD_LOCAL.set(exampleContextData);
    }

    public void remove(){
        CONTEXT_DATA_THREAD_LOCAL.remove();
    }

    public ExampleContextData get(){
        return CONTEXT_DATA_THREAD_LOCAL.get();
    }

    public String getJwtToken() {
        return CONTEXT_DATA_THREAD_LOCAL.get().getJwtToken();
    }

    public String getUserName() {
        return CONTEXT_DATA_THREAD_LOCAL.get().getUserName();
    }


    @Override
    public String toString() {
        if(CONTEXT_DATA_THREAD_LOCAL.get() == null){
            return null;
        }
        return CONTEXT_DATA_THREAD_LOCAL.get().toString();
    }


}

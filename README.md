# Spring Boot Custom Context Data Library
Custom Spring Boot library for binding contextual data across REST calls and JMS messaging.

# Prerequisites 
**NOTE: Although IntelliJ should not be required, the instructions under Step Through the Code, were only performed in IntelliJ.**
* Java 11 or above
* Docker  

# Quick Start 
* Cd to the root of the multi-module project: ```contextdatalib-for-http-and-jms```
* Startup ActiveMQ by executing the following script and selecting 'start' at the prompt.
```./docker-compose/docker-compose.sh```
* Start up the auth Spring Boot app by executing the following:  
```gradle auth:bootrun```
* Start up the moduleA Spring Boot app by executing the following:  
```gradle moduleA:bootrun```
* Start up the moduleB Spring Boot app by executing the following:  
```gradle moduleB:bootrun```
* Get a JWT token by executing the following cURL:  
```curl --location --request POST 'http://localhost:8070/auth' --header 'Content-Type: application/json' --data-raw '{"username":"test"}'```  
* Execute the following cURL using the JWT token that is returned in the response in the Authorization header:  
```curl --location --request GET 'http://localhost:8080/modulea/examples' --header 'Authorization: Bearer <JWT>'```  
* For example:  
```curl --location --request GET 'http://localhost:8080/modulea/examples' --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2YWwifQ.Dh909WZ2M3iAYC6BVEVdLRZxvIOof8Jd169pQjdXsKQ'```

# Step Through the Code
**NOTE: Although IntelliJ should not be required, the instructions under Step Through the Code, were only performed in IntelliJ.**
* Import the multi-module project into your IDE.
* Create a Run / Debug Configuration for docker-compose.sh, the auth app, the moduleA app, and the moduleB app.
* Execute docker-compose.sh to startup ActiveMQ.
* Execute the debug configuration for the auth app, the moduleA app, and the moduleB app.
* Add the following breakpoints:
    * common library:
        * CommonServletFilter line #32: Intercept the HTTP request to retrieve the JWT, authenticate it, parse the claims, and add context data.   
        * CommonWebClient line #27: Add the JWT to retrieve the JWT from context data and it to the Authorization header.     
        * CommonJmsInterceptor lines #64: Intercept sending the JMS message to retrieve the JWT from context data and add it to the Authorization property.
        * CommonJmsInterceptor lines #82: Intercept receiving the JMS message to read the JWT from the Authorization property, authenticate it, parse the claims, and add context data.
        * CommonJmsInterceptor lines #110: Intercept completion of JMS message processing to remove the context data.
        * CommonServletFilter line #46: Remove the context data.
    * moduleA app:
        * ModuleAResource line #26: Receive the request.
        * JmsTopicPublisher line #34: Send a JMS message to ModuleB.
        * ModuleAService line #36: Send an HTTP request to ModuleB.
    * moduleB app:
        * ModuleBResource line #26: Receive the request.
        * ModuleBService line #20. 
        * JmsTopicSubscriber line #29: Receive the JMS message.  
* Get a JWT token by executing the following cURL and replace <username> with your username.
    * Execute:  
    ```curl --location --request POST 'http://localhost:8070/auth' --header 'Content-Type: application/json' --data-raw '{"username":<username>}'```  
    * For example, the following cURL will retreive a JWT for username "mytest".  
    ```curl --location --request POST 'http://localhost:8070/auth' --header 'Content-Type: application/json' --data-raw '{"username":"mytest"}'```  
* Submit a request to ModuleA
    * Execute the following cURL using the JWT token that is returned in the response in the Authorization header:    
    ```curl --location --request GET 'http://localhost:8080/modulea/examples' --header 'Authorization: Bearer <JWT>'```  
    * For example, the followng cURL will make a request with JWT eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0In0.nslK4smd63aJAr6VuUA7QMHkwHWg5K1a8y0zK7sjVC8.  
    ```curl --location --request GET 'http://localhost:8080/modulea/examples' --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0In0.nslK4smd63aJAr6VuUA7QMHkwHWg5K1a8y0zK7sjVC8'```
* Watch for "mytest" in the response and the logs!!!
        


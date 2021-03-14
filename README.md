# Context Data Library
Custom Spring Boot library for binding contextual data across REST calls and JMS messaging.

# Prerequisites 
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


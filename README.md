# spring-security-jwt
Generate a JWT with validated user credential and use the generated JWT to access an endpoint. It has the following dependencies:

    * JDK 1.8 or 11
    * Spring Boot 2.2.0.RELEASE
    * JJWT 0.9.1
    * Swagger 2

# Run/Debug
* Start the main Java application `SpringJwtApplication` or run the MAVEN build `spring-boot:run`
* Open the Swagger UI in a browser with the following url: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* Expand the `Jwt Authentication Controller` and `/authenticate` endpoint
* Run a `POST` request with the following payload and it will return a JWT

``` json
{
"username":"username",
"password": "password"
}
```
* Copy the generated JWT token and prefix it with `Bearer`, similar to the following one

```
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE2OTQ0OTg1NzEsImlhdCI6MTY5NDQ4MDU3MX0.b9BYxjVLeJ4FTC40RRRMNXQ81uF0W0g_usS8wRqVisd_VEDvYYZa7gquFQnWDVl14Iu6d_KqkfdODFLxMk5wSg
```
* Expand `Hello World Controller` and `/hello` endpoint
* Put the JWT in the `Authorization` field and change `Response Content Type` to `text/plain`
* `Hello World` will be returned when the request is run

NOTE: The username and password are hardcoded in the example and the JWT will not be generated if changed

# Implementation Highlight
There are three major parts in this project: **User authentication/JWT creation**, **protected endpoint/JWT verification** and **Swagger UI**.

## User authentication/JWT creation
This part contains `JwtRequest`, `JwtResponse`, `JwtAuthenticationController`, `JwtUserDetailsService`, `WebSecurityConfig` and `JwtTokenUtil` 
### JwtRequest
This is the input to the authentication endpoint. It contains the username and password that need to be verified.

### JwtResponse
This is output of authentication endpoint. It contains the JWT for the valid user.

### JwtAuthenticationController
It exposes a **POST** API `/authenticate`, which takes a `JwtRequest` and returns a `JwtResponse` for the valid user. It calls `AuthenticationManager` to authenticate the user. If the credentials are valid, a JWT token is created using the `JWTTokenUtil`.

### JWTUserDetailsService
`JWTUserDetailsService` implements the Spring Security `UserDetailsService` interface and is the key module to authenticate the user. 

`AuthenticationManager` uses it to authenticate the user and `JwtTokenUtil` uses it to generate the JWT. 
The original `loadUserByUsername` fetches user details from the database using the username. It's overridden to getg the user details from a hardcoded user list. The password for a user is stored in encrypted format using BCrypt which is available at [Online Bcrypt Generator](https://www.javainuse.com/onlineBcrypt).

### WebSecurityConfig
It extends `WebSecurityConfigurerAdapter` and provides the configuration of the web security. It is a convenience class that allows customization to both WebSecurity and HttpSecurity.

It attaches the `UserDetailsService` to `AuthenticationManager` for user authentication and enable/disable the security check for the endpoints. It also adds `JwtRequestFilter` to enable the JWT verification. `JwtAuthenticationEntryPoint` is configured to handle the user authentication failure as well.

### JwtTokenUtil
`JwtTokenUtil` is a utility class for generating and verifying JWT. It also collects the claims in JWT. 
It uses a base64 encoded issuer ID to encrypt/decrypt JWT. The issuer ID is collected from `application.properties` as `jwt.secret`. The secret key is combined with the header and the payload to create a unique hash. 

It leverages the features provided by `io.jsonwebtoken.Jwts`, which is included by adding the following dependency in `pom.xml`.

```
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt</artifactId>
			<version>0.9.1</version>
		</dependency>

```
## Protected endpoint/JWT verification
This part contains `HelloWorldController`, `JwtRequestFilter`, `JwtAuthenticationEntryPoint` and `SpringJwtApplication`.

### HelloWorldController
It is a simple **GET** endpoint which returns a `Hello World` string.

### JwtRequestFilter
`JwtRequestFilter` is another key module in the JWT authentication. It extends the Spring Web Filter `OncePerRequestFilter` class and checks all incoming request for a valid JWT. 

It uses `JwtTokenUtil` to extract username from JWT and gets the saved password with `JwtUserDetailsService`. If the saved password matches the one in the JWT, it saves the authenticated information in context of `SecurityContextHolder`, which will in turn be checked by the other security filters for the access to the protected endpoints.

### JwtAuthenticationEntryPoint
`JwtAuthenticationEntryPoint` is configured by `WebSecurityConfig` to handle the authentication failure. It simply returns the authentication failure message.

### SpringJwtApplication
A simple entry point to start the application.

## Swagger2
*`SwaggerConfig` is the class to configure Swagger2. It creates a `Authorization` field for all endpoints which allows the JWT to be entered and sent as request header. It accepts the request and provides response for both JSON and plain text.
* It is enabled by adding the following dependencies to `pom.xml`.

```
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.7.0</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.7.0</version>
		</dependency>
```

# Source
This project is built base on the following tutorial
https://www.javainuse.com/spring/boot-jwt
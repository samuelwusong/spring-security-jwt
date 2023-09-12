# spring-security-jwt
Generate a JWT tocken with Spring and use the generated JWT to access an endpoint

#Run/Debug
* Run a **POST** request on Postman with the following payload on the URL http://localhost:8080/authenticate

```
{
"username":"javainuse",
"password": "password"
}
```
* Copy the generated JWT token and prefix it with **Bearer**

```
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE2OTQ0OTg1NzEsImlhdCI6MTY5NDQ4MDU3MX0.b9BYxjVLeJ4FTC40RRRMNXQ81uF0W0g_usS8wRqVisd_VEDvYYZa7gquFQnWDVl14Iu6d_KqkfdODFLxMk5wSg
```
* Run a **GET** request on Postman with the following Headers on the URL http://localhost:8080/hello

```
Authentication: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE2OTQ0OTg1NzEsImlhdCI6MTY5NDQ4MDU3MX0.b9BYxjVLeJ4FTC40RRRMNXQ81uF0W0g_usS8wRqVisd_VEDvYYZa7gquFQnWDVl14Iu6d_KqkfdODFLxMk5wSg
```
* It should complete with OK
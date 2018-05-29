# demo-profile-api

## Instructions
### Using Maven
Test and run using following commands.
```
mvn test
mvn spring-boot:run
```
Open [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) in browser to see API documentation

### Docker Image
Run following commands to download and run docker image with basic implementation.
```
$ docker pull mbilalf/demo-profile-api:init
$ docker run -p 90:8080 mbilalf/demo-profile-api:init
```
Open [localhost:90/swagger-ui.html](http://localhost:90/swagger-ui.html) in browser to see API documentation

## Assumptions:
- Some API Consumers are: Backed Enterprise CRM, Customer facing web and mobile apps.
- I have considered User/password a separate part of Customer Account settings and not profile, thus not included in UserProfile.

## Testing

Three type of tests can be written:
1. Unit tests on functionality that don't require spring environment. No tests written as there is not much functionality.

2. Unit tests on code that require spring environment (e.g. controllers) but should be isolated from other layers of code or integrations (db, queues etc).
*UserProfileControllerTest* covers few of tests.

3. Integration testing - implemented as unit test without mocking the dependencies. This will expand and will require more
configuration when integrations increase. *UserProfileControllerIntegrationTest* provides a sample integration test.


## Security
Possible security solution — in micro-service pattern ideally security should be OAuth2 based.

A separate Authorisation Server (AS) should be used to get access token with limited expiry time.

- Api Client gets an access token from AS.
- Token should be part of every api call to profile-api service.
- profile-api should access AS to verify token


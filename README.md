# demo-profile-api

## Instructions

#### Update 30-Jun-2018
- Added Jacoco for code coverage. Code coverage for controller and service package is __99%__.

### Using Maven
Test and run using following commands.
```
mvn test jacoco:report
mvn spring-boot:run
```
Open link [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to see API documentation

### Docker Image
Run following commands to download and run docker image.
```
$ docker pull mbilalf/demo-profile-api
$ docker run -p 90:8080 --env SPRING_PROFILE=test mbilalf/demo-profile-api
```
Open link [localhost:90/swagger-ui.html](http://localhost:90/swagger-ui.html) to see API documentation

## Assumptions:
- Understanding: Assuming micorservice based pattern, ProfileAPI is part of backend enterpise CRM. It will have direct access to db or use a composite or core service to do IO on database.
- API will be consumed by customer facing web and mobile apps.
- I have considered User/password a separate part of Customer Account settings and not profile, thus not included in UserProfile.
- I focussed more on API documentation and delivery of the application. DB implementation is missing, cuz as per my available time, I ranked it as lowest requirement. I hope this is not considered as a deal breaker.

## Testing

Three type of tests can be written:
1. Unit tests on functionality that don't require spring environment. No tests written as there is not much functionality.

2. Unit tests on code that require spring environment (e.g. controllers, services) but should be isolated from other layers of code or integrations (db, queues etc).
   - *UserProfileControllerTest* covers few of tests. Tests in this class would look more relevant when DB integration is done. Serive and controller layer do no manipulation and simply handover the data from Db(Map currenlty). In that case separate unit test for UserProfileService should also be added.

3. Integration testing - implemented as unit test without mocking the dependencies. This will expand and will require more
configuration when integrations increase. *UserProfileControllerIntegrationTest* provides a sample integration test.


## Security
Possible security solution — in micro-service pattern ideally security should be OAuth2 based.

A Authorisation Server (AS) should be used to get access token with limited expiry time.

- Api Client gets an access token from AS. 
   - Web client will get redirected to Authentication Server page for authentication and redirected back an authorisation code. It will get access_token from Authentication server using authorization code.
   - Mobile app will pass user/pass to Authentication server and get access_token
- Token should be part of every api call to profile-api service.


## Integrations Diagram
##### _Fig1: Integration diagram with web client_


![Integration diagram with web security comms](/docs/Model-Auth-Web.png)

##### _Fig2: Integration diagram with mobile client_

![Integration diagram with mobile security comms](/docs/Model-Auth-Mobile.png)



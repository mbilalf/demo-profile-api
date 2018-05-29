# demo-profile-api

## Assumptions:
- Some API Consumers are: Backed Enterprise CRM, Customer facing web and mobile apps.
- I have considered User/password a separate part of Customer Account settings and not profile, thus not included in UserProfile.

## Testing

Three type of tests can be written for an application.
1. Unit tests on functionality that don't require spring environment. No tests written as there is not much functionality.
2. Unit tests on controllers that require spring environment but should be isolated from integrations (db, queues etc).
UserProfileControllerTest covers those tests.
3. Integration testing - implemented as unit test without mocking the dependencies. This will expand and will require more
configuration when integrations increase.


## Security
Possible security solution. In micro-service pattern security should be OAuth2 based.

A separate Authorisation Server (AS) should be used to get access token with limited expiry time.

- Api Client gets an access token from AS.
- Token should be part of every api call to profile-api service.
- profile-api should access AS to verify

## Docker Image
Run following commands to download and docker image with basic implementation.
```
$ docker pull mbilalf/demo-profile-api:init
$ docker run -p 90:8080 mbilalf/demo-profile-api:init
```

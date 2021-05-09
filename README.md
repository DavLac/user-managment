# Simple User CRUD

## User entity
```
  {
      "id": Long,
      "name": String,
      "password": String,
      "role": ['ADMIN', 'CLIENT'],
      "creationDateTime": Date
  }
```


## Endpoints
### User entity
- GET /users
  ```
  # parameters #
  pageNumber: int,
  size: int
  ```
- GET /users/{name}
- POST /users
  ```
  # body #
  {
    "name": String,
    "password": String,
    "role": ['ADMIN', 'CLIENT']
  }
  ```
- PUT /users/{name}
  ```
  # body #
  {
    "name": String,
    "password": String,
    "role": ['ADMIN', 'CLIENT']
  }
  ```
- DELETE /users/{name}

### Authentication entity
- GET /auth/login
  ```
  # body #
  {
    "name": String,
    "password": String
  }
  ```
Response :
  ```
  Encoded base64 token.
  Decoded value :
  {
    "name": String,
    "role": ['ADMIN', 'CLIENT'],
    "expirationTime": Long,
    "refreshTokenTime": Long
  }
  ```

## Run the app

### with Docker
Run `docker-compose up` command

### with Maven
Check the **Prerequisites** section and after run `mvn spring-boot:run` command

## Prerequisites

### Database
This app uses a MySQL database running on : `localhost:3306`

Check `spring.datasource` property to get the right credentials for the database.
Check `docker-compose.yml` file to get the good env variables/docker image to run the database.

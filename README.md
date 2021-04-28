# Simple User CRUD

## User entity
```
  {
    "id": Long,
    "name": String,
    "password": String,
    "creationDateTime": Date
  }
  ```


## Endpoints
- GET /users
- GET /users/{id}
- POST /users
  ```
  # body #
  {
    "name": String,
    "password": String
  }
  ```
- PUT /users/{id}
  ```
  # body #
  {
    "name": String,
    "password": String
  }
  ```
- DELETE /users/{id}

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

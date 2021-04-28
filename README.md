# Simple User CRUD

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

## Prerequisites

### Database
This app uses a MySQL database running on : `localhost:3306`

Check `spring.datasource` property to get the right credentials for the database.
Check `docker-compose.yml` file to get the good env variables/docker image to run the database.

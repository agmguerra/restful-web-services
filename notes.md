# Restful Web Services

Social Media Application

User -> Posts

- Retrieve all users - GET /users
- Create a user      - POST /users
- Retrieve one user  - GET /users/{id} -> /users/1
- Delete a user      - DELETE /users/{id} -> /users/1

- Retrieve all posts for a user - GET /users/{id}/posts
- Create a post for a user      - POST /users/{id}/posts
- Retrieve details of a post    - GET /users/{id}/posts/{post_id}

create table user (
id integer not null,
birthdate timestamp,
name varchar(255),
primary key(id)
)

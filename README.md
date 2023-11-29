#### How to Run

- For Front End
  - Open app folder in Andriod Studio
- For Back End 
  - cd command to the "backend" folder
  - run "npm install"
  - run npm start

###### Note App will not work properly without the backend server running

#### Instructions

Develop an android app similar to [this](https://drive.google.com/file/d/1roX9hy1ZzaMzSAdy2XXnRK-CaV3uLcD5/view?usp=sharing).

#### Emulator

Use Pixel 7 Pro API 34

#### Requirements

**Implement backend with these API endpoints (any language)**
- `GET /users`
  - Returns list of users
  - Sample response data
    ```
    [
      {
       "id": 1,
       "first_name": "John",
       "last_name": "Doe",
       "email": "john.doe@example.com"
      },
      {
       "id": 2,
       "first_name": "Alice",
       "last_name": "Smith",
       "email": "alice.smith@example.com"
      },
    ] 
    ```
- `GET /users/<id>`
  - Returns detailed user
  - Sample response data
    ```
    {
      "id": 1,
      "first_name": "John",
      "last_name": "Doe",
      "email": "john.doe@example.com",
      "favorite_food": "Pizza"
    }
    ```
- `POST /login`
  - Login endpoint
  - Sample request data
    ```
    {
      "email": "john.doe@example.com",
      "password": "password123"
    }
    ```
  - Sample response data
    ```
    {"id": 1}
    ```
  - If user does not exist, raise 404 not found

**Sample users data**

```
USERS = [
    {
        "id": 1,
        "first_name": "John",
        "last_name": "Doe",
        "favorite_food": "Pizza",
        "email": "john.doe@example.com",
        "password": "password123"
    },
    {
        "id": 2,
        "first_name": "Alice",
        "last_name": "Smith",
        "favorite_food": "Sushi",
        "email": "alice.smith@example.com",
        "password": "securepass456"
    },
    {
        "id": 3,
        "first_name": "Michael",
        "last_name": "Johnson",
        "favorite_food": "Tacos",
        "email": "michael.johnson@example.com",
        "password": "mysecretpw"
    },
    {
        "id": 4,
        "first_name": "Emily",
        "last_name": "Davis",
        "favorite_food": "Pasta",
        "email": "emily.davis@example.com",
        "password": "pastaLover789"
    },
    {
        "id": 5,
        "first_name": "Sarah",
        "last_name": "Wilson",
        "favorite_food": "Burgers",
        "email": "sarah.wilson@example.com",
        "password": "burger1234"
    }
]
```

# CDIT Mini Project Assignment
This project contains a web application with only one endpoint - `/users`.

## Acceptance Criteria
There are 2 acceptance criteria for this project. They are described as follow.

### Acceptance Criteria 1
This web application should be able to:
- Read a csv file and insert into the database

### Acceptance Criteria 2
This web application should be able to:
- Expose an endpoint `/users` that returns a JSON object containing the list
  of users with salary (0 <= salary <= 4000)
    - The JSON object should be of the following format:
      ```
      {
          "results": [
              {
                  "name": "John",
                  "salary": 2500.05
              },
              {
                  "name": "Mary Posa",
                  "salary": 4000.00
              }
          ]
      }
      ```

## Technologies
It is required to use Java and the Spring Boot framework to develop this project.

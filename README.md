# Running the Application

This project utilizes a Docker Compose file named `compose.yaml`. To get started, you'll need to install and start the Docker engine. Once Docker is up and running, you can proceed to start the application.

## Database Setup

Upon starting the application, a database will be automatically created and populated with test data. This ensures that you have a ready-to-use database with pre-defined data for development or testing purposes.

## Accessing the Application

The application will be accessible on your local machine, specifically at `localhost` on port `8080`. You can access it by opening a web browser and navigating to `http://localhost:8080`.

## Steps to Run the Application

1. **Install Docker Engine**: Ensure that Docker is installed on your machine. If not, you can download and install it from the [official Docker website](https://www.docker.com/products/docker-desktop).

2. **Start Docker**: Once Docker is installed, start the Docker engine. This can typically be done through the Docker Desktop application or by using the command line, depending on your operating system.

3. **Navigate to the Project Directory**: Open a terminal or command prompt and navigate to the directory containing the `compose.yaml` file.

4. **Starting the Docker Container**: To initiate the database using Docker Compose, execute the following command:: 

To initiate the application using Docker Compose, execute the following command:

```
docker-compose up
```

This command will build and start the containers defined in the `compose.yaml` file.

5. **Start the Application**: Run the app as usual with intellij idea.

6. **Access the Application**: Open a web browser and navigate to `http://localhost:8080` to access the application.

## News REST Controller Overview

The `NewsRestController` is a RESTful API controller designed for managing news items. It provides a comprehensive set of endpoints for creating, reading, updating, and deleting news records. The controller leverages a `NewsService` to handle the business logic, ensuring a clean separation of concerns between the API layer and the service layer.

### Endpoints

- **Create News Record**: `POST /news` - Accepts a `CreateNewsDto` object, validates it, and uses the `NewsService` to create a new news record.
- **Read News Record by ID**: `GET /news/{id}` - Retrieves a specific news record by its ID using the `NewsService`.
- **Read Pageable List of News Records**: `GET /news/pageable` - Accepts pagination parameters and a time period type, and retrieves a pageable list of news records that match the criteria.
- **Update News Record**: `PATCH /news/{id}` - Accepts an `UpdateNewsDto` object, validates it, and uses the `NewsService` to update the news record with the specified ID.
- **Delete News Record**: `DELETE /news/{id}` - Deletes a specific news record by its ID using the `NewsService`.

Each endpoint returns a `ResponseEntity` indicating the success or failure of the operation, providing clear feedback to the client.

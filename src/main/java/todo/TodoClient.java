package todo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A client for interacting with the JSONPlaceholder Todo API.
 * This class provides methods to perform CRUD operations on Todo items.
 */
public class TodoClient {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/todos";
    private final HttpClient client;
    private final ObjectMapper mapper;
;
    

    /**
     * Constructs a new TodoClient with default HttpClient and ObjectMapper instances.
     */
    public TodoClient() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    /**
     * Retrieves all todos from the API.
     *
     * @return A list of all Todo items
     * @throws IOException If there's an error reading the response
     * @throws InterruptedException If the HTTP request is interrupted
     */
    public List<Todo> getAllTodos() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(), new TypeReference<>() {});
    }

    /**
     * Finds a specific Todo by its ID.
     *
     * @param id The ID of the Todo to retrieve
     * @return The Todo with the specified ID
     * @throws IOException If there's an error reading the response
     * @throws InterruptedException If the HTTP request is interrupted
     * @throws TodoNotFoundException If no Todo exists with the specified ID
     */
    public Todo findById(int id) throws IOException, InterruptedException, TodoNotFoundException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/" + id))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 404) {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }
        return mapper.readValue(response.body(), Todo.class);
    }

    /**
     * Creates a new Todo item.
     *
     * @param todo The Todo object to create
     * @return The HTTP response containing the created Todo
     * @throws IOException If there's an error sending the request or reading the response
     * @throws InterruptedException If the HTTP request is interrupted
     * @throws IllegalArgumentException If the todo parameter is null
     */
    public HttpResponse<String> create(Todo todo) throws IOException, InterruptedException {
        if (todo == null) {
            throw new IllegalArgumentException("Todo cannot be null");
        }

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(todo)))
            .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Updates an existing Todo item.
     *
     * @param todo The Todo object with updated values
     * @return The HTTP response containing the updated Todo
     * @throws IOException If there's an error sending the request or reading the response
     * @throws InterruptedException If the HTTP request is interrupted
     * @throws IllegalArgumentException If the todo parameter is null or has no ID
     */
    public HttpResponse<String> update(Todo todo) throws IOException, InterruptedException {
        if (todo == null) {
            throw new IllegalArgumentException("Todo cannot be null");
        }

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/" + todo.id()))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(todo)))
            .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Deletes a Todo item.
     *
     * @param todo The Todo object to delete
     * @return The HTTP response confirming the deletion
     * @throws IOException If there's an error sending the request or reading the response
     * @throws InterruptedException If the HTTP request is interrupted
     * @throws IllegalArgumentException If the todo parameter is null or has no ID
     */
    public HttpResponse<String> delete(Todo todo) throws IOException, InterruptedException {
        if (todo == null) {
            throw new IllegalArgumentException("Todo cannot be null");
        }

        var request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/" + todo.id()))
            .DELETE()
            .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

package test.java;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import todo.*;

class TodoClientTest {
   
        //system under test (sut)
        TodoClient todoClient = new TodoClient();

        //integration tests
        @Test
        void testGetAllTodos() throws IOException, InterruptedException {
            TodoClient todoClient = new TodoClient();
            List<Todo> todos = todoClient.getAllTodos();
            assertEquals(200, todos.size());
        }

        //List<Todo> todos = todoClient.getAllTodos();
        @Test
        void shouldReturnTodoGivenValid() throws IOException, InterruptedException{
            Todo todo = todoClient.findById(1);
            assertEquals(1, todo.userId());
            assertEquals(1, todo.id());
            assertEquals("delectus aut autem", todo.title());
            assertEquals(todo.completed(), false);
        }

        @Test
        void shouldThrowNotFoundExceptionGivenInvalidId() {
           TodoNotFoundException todoNotFound = assertThrows(TodoNotFoundException.class, () -> todoClient.findById(200));
           assertEquals("Todo not found", todoNotFound.getMessage());
        }

        @Test
        void shouldUpdate() throws IOException, InterruptedException {
            Todo todo = new Todo(1,1, "new title", true);
            HttpResponse<String> response = todoClient.update(todo);
            assertEquals(200, response.statusCode());
        }

        @Test
        void shouldDelete() throws IOException, InterruptedException {
            Todo todo = todoClient.findById(1);
            HttpResponse<String> response = todoClient.delete(todo);
            assertEquals(200, response.statusCode());
        }

        
        //assertEquals(200, todos.size());
   
    
}

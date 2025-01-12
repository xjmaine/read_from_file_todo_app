package todo.ReadFromJson;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import todo.Todo;
import todo.TodoNotFoundException;

public class ReadFromJson {
    private final ObjectMapper mapper;
    private final List<Todo> todos;

    public ReadFromJson() throws IOException{
        mapper = new ObjectMapper();
        todos = mapper.readValue(
            getClass().getClassLoader().getResourceAsStream("database/data.json"),
            new TypeReference<List<Todo>>() {}
        );
    }

    //fetch all tasks from json
        public List<Todo> getAllTodos(){
            return todos;
        }

        //fetch task from json with ID
        public Todo getTodoById(int id) throws TodoNotFoundException{
            return todos.stream()
                .filter(todo -> todo.id() == id)
                .findFirst()
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
        }

        //create task
        public Todo create(Todo todo){
            todos.add(todo);
            return todo;
        }

        //update task
        public Todo update(Todo todo) throws TodoNotFoundException{
            int index = findTodoIndex(todo.id());
            todos.set(index, todo);
            return todo;
        }

        //delete task
        public void delete(Todo todo) throws TodoNotFoundException{
            int index = findTodoIndex(todo.id());
            if(index >= 0){
                todos.remove(index);
                System.out.println("Todo with id: " + todo.id() + " deleted successfully");
            }

            else{
                throw new TodoNotFoundException("Todo not found with id: " + todo.id());
            }

        }


        private int findTodoIndex(int id) throws TodoNotFoundException{
            for(int i=0; i<todos.size(); i++){
                if(todos.get(i).id() == id){
                    return i;
                }
            }
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }


        //get total number of tasks
        public int totalTodos(){
            return todos.size();
        }
}
    

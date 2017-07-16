package todomvc

class TodoRepository(private val todos: MutableList<Todo> = mutableListOf()) {

    fun addTodo(todo: Todo) {
        todos.add(todo)
    }

    fun getTodoCount() = todos.size

}
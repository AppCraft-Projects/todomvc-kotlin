package todomvc

import org.w3c.dom.HTMLInputElement
import kotlin.dom.addClass
import kotlin.dom.removeClass

class TodoRepository(private val todos: MutableMap<Int, Todo> = mutableMapOf()) {

    private val emptyListeners = mutableListOf<() -> Unit>()

    fun addTodo(todo: Todo) {
        todos.put(todo.id, todo)
        if (todos.size == 1) {
            triggerEmptyListeners()
        }
    }

    fun fetchTodoById(id: Int) = todos[id]

    fun deleteTodoById(id: Int) {
        val todoToDelete = todos.remove(id)
        todoToDelete?.element?.remove()
        if (todos.isEmpty()) {
            triggerEmptyListeners()
        }
    }

    fun toggleCompletedOnAll() {
        if (onlyHasCompletedTodos()) {
            todos.values.forEach {
                it.completed = false
                it.element.removeClass("completed")
                it.element.getChildBySelector<HTMLInputElement>(".toggle").toggle()
            }
        } else {
            todos.values
                    .filterNot { it.completed }
                    .forEach {
                        it.completed = true
                        it.element.addClass("completed")
                        it.element.getChildBySelector<HTMLInputElement>(".toggle").toggle()
                    }
        }
    }

    fun clearCompleted() {
        val idsToRemove = todos.values.filter { it.completed }.map {
            it.element.remove()
            it.id
        }
        idsToRemove.forEach {
            todos.remove(it)
        }
        if (todos.isEmpty()) {
            triggerEmptyListeners()
        }
    }

    fun fetchTodoCount() = todos.count()

    fun fetchCompletedCount() = todos.values
            .filter { it.completed }
            .count()

    /**
     * Triggered when the todos become empty or are no longer empty.
     */
    fun addEmptyListener(listener: () -> Unit) = emptyListeners.add(listener)

    private fun triggerEmptyListeners() = emptyListeners
            .forEach { it.invoke() }

    private fun onlyHasCompletedTodos() = todos.values
            .filterNot { it.completed }
            .isEmpty()

}
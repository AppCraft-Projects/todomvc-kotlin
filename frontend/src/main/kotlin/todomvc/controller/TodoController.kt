package todomvc.controller

import org.w3c.dom.HTMLUListElement
import todomvc.HtmlComponentFactory
import todomvc.component.TodoComponent
import todomvc.data.Todo
import todomvc.data.TodoChange
import todomvc.data.TodoEdit
import todomvc.event.EventBus
import todomvc.event.EventType.TODOS_EMPTY_OR_NO_LONGER_EMPTY
import todomvc.event.EventType.TODO_CHANGED
import todomvc.extension.fetchElementBySelector
import kotlin.browser.document

class TodoController {

    private var counter = 1
    private val todos: MutableMap<Int, Pair<Todo, TodoComponent>> = mutableMapOf()
    private val todoList = document.fetchElementBySelector<HTMLUListElement>(".todo-list")

    fun addTodo(text: String) {
        val id = counter
        counter++
        val todoElement = HtmlComponentFactory.createTodoListElement(text)
        val todo = Todo(
                id = id,
                task = text)
        todos.put(todo.id, Pair(todo, TodoComponent(todo.id, todoElement)))
        todoList.appendChild(todoElement)
        if (todos.size == 1) {
            emitTodoEmptyEvent() // it was 0 before
        }
        emitTodoChangedEvent()
    }

    fun editTodo(todoEdit: TodoEdit) {
        todoEdit.let { (id, newText) ->
            todos[id]?.first?.task = newText
        }
        emitTodoChangedEvent()
    }

    fun deleteTodoById(id: Int) {
        todos.remove(id)?.let { (_, todoComponent) ->
            todoComponent.remove()
            if (todos.isEmpty()) {
                emitTodoEmptyEvent()
            }
            emitTodoChangedEvent()
        }
    }

    fun completeTodo(id: Int) {
        todos[id]?.first?.toggleCompleted()
        emitTodoChangedEvent()
    }

    fun toggleCompletedOnAll() {
        if (onlyHasCompletedTodos()) {
            todos.values.forEach { (todo, component) ->
                todo.completed = false
                component.toggleCompleted()
            }
        } else {
            todos.values
                    .filterNot { it.first.completed }
                    .forEach { (todo, component) ->
                        todo.completed = true
                        component.toggleCompleted()
                    }
        }
        emitTodoChangedEvent()
    }

    fun clearCompleted() {
        val idsToRemove = todos.values
                .filter { it.first.completed }
                .map { (todo, component) ->
                    component.remove()
                    todo.id
                }
        idsToRemove.forEach {
            todos.remove(it)
        }
        if (todos.isEmpty()) {
            emitTodoEmptyEvent()
        }
        emitTodoChangedEvent()
    }

    private fun emitTodoEmptyEvent() {
        EventBus.emit(TODOS_EMPTY_OR_NO_LONGER_EMPTY, todos.count())
    }

    private fun emitTodoChangedEvent() {
        println("Emitting too change event. Todos: ${todos.values.map { it.first }}")
        EventBus.emit(TODO_CHANGED, TodoChange(
                totalCount = todos.count(),
                remainingCount = todos.filterNot { it.value.first.completed }.count(),
                completedCount = todos.filter { it.value.first.completed }.count()))
    }

    private fun onlyHasCompletedTodos() = todos.values
            .filterNot { it.first.completed }
            .isEmpty()

}
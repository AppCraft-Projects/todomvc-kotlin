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
import todomvc.store.TodoStorage
import kotlin.browser.document

class TodoController(private val todoStorage: TodoStorage) {

    private var counter = 1
    private val todos: MutableMap<Int, Pair<Todo, TodoComponent>> = mutableMapOf()
    private val todoList = document.fetchElementBySelector<HTMLUListElement>(".todo-list")

    fun init() {
        todoStorage.loadTodos().map {
            println("Loading Todo from local storage: $it")
            addTodo(it.title, it.id, it.completed)
            it.id
        }.max()?.let { maxId ->
            counter = maxId + 1
            println("Counter was set to $counter after local storage load.")
        }
    }

    fun addTodo(text: String) {
        val id = counter
        counter++
        addTodo(text, id)
    }

    fun editTodo(todoEdit: TodoEdit) {
        todoEdit.let { (id, newText) ->
            todos[id]?.first?.let { todo ->
                todo.title = newText
                todoStorage.update(todo)
            }
        }
        emitTodoChangedEvent()
    }

    fun deleteTodoById(id: Int) {
        todos.remove(id)?.let { (todo, todoComponent) ->
            todoStorage.remove(todo)
            todoComponent.remove()
            if (todos.isEmpty()) {
                emitTodoEmptyEvent()
            }
            emitTodoChangedEvent()
        }
    }

    fun completeTodo(id: Int) {
        todos[id]?.first?.let { todo ->
            todo.toggleCompleted()
            todoStorage.update(todo)
        }
        emitTodoChangedEvent()
    }

    fun toggleCompletedOnAll() {
        if (onlyHasCompletedTodos()) {
            todos.values.forEach { (todo, component) ->
                todo.completed = false
                todoStorage.update(todo)
                component.toggleCompleted()
            }
        } else {
            todos.values
                    .filterNot { it.first.completed }
                    .forEach { (todo, component) ->
                        todo.completed = true
                        todoStorage.update(todo)
                        component.toggleCompleted()
                    }
        }
        emitTodoChangedEvent()
    }

    fun clearCompleted() {
        val idsToRemove = todos.values
                .filter { it.first.completed }
                .map { (todo, component) ->
                    todoStorage.remove(todo)
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

    private fun addTodo(text: String, id: Int, completed: Boolean = false) {
        val todoElement = HtmlComponentFactory.createTodoListElement(text)
        val todo = Todo(
                id = id,
                title = text,
                completed = completed)

        val component = TodoComponent(todo.id, todoElement)
        todos.put(todo.id, Pair(todo, component))
        todoStorage.create(todo)
        todoList.appendChild(todoElement)
        if(completed) {
            component.toggleCompleted()
        }
        if (todos.size == 1) {
            emitTodoEmptyEvent() // it was 0 before
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
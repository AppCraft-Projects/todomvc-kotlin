package todomvc

import todomvc.component.*
import todomvc.controller.TodoController
import todomvc.data.TodoEdit
import todomvc.event.EventBus
import todomvc.event.EventType

fun main(args: Array<String>) {
    val todoController = TodoController()

    ClearCompletedComponent()
    FooterComponent()
    MainComponent()
    NewTodoComponent()
    TodoCountComponent()
    ToggleAllComponent()

    EventBus.subscribe<String>(EventType.ADD_TODO, { (text) ->
        todoController.addTodo(text)
    })
    EventBus.subscribe<TodoEdit>(EventType.EDIT_TODO, { (todoEdit) ->
        todoController.editTodo(todoEdit)
    })
    EventBus.subscribe<Int>(EventType.DELETE_TODO, { (id) ->
        todoController.deleteTodoById(id)
    })
    EventBus.subscribe<Int>(EventType.COMPLETE_TODO, { (id) ->
        todoController.completeTodo(id)
    })
    EventBus.subscribe<Unit>(EventType.TOGGLE_COMPLETED_ON_ALL, {
        todoController.toggleCompletedOnAll()
    })
    EventBus.subscribe<Unit>(EventType.CLEAR_COMPLETED, {
        todoController.clearCompleted()
    })
}
package todomvc

import kotlin.browser.document

fun main(args: Array<String>) {
    val todoRepository = TodoRepository()

    val elements = TodoMVCElements(
            todoRepository = todoRepository,
            main = document.fetchElementBySelector(".main"),
            footer = document.fetchElementBySelector(".footer"),
            newTodo = document.fetchElementBySelector(".new-todo"),
            todoList = document.fetchElementBySelector(".todo-list"),
            todoCount = document.fetchElementBySelector(".todo-count"),
            clearCompleted = document.fetchElementBySelector(".clear-completed"),
            toggleAll = document.fetchElementBySelector(".toggle-all"))

    todoRepository.addEmptyListener {
        elements.toggleMainAndFooterVisibility()
    }
}
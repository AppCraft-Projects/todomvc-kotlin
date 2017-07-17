package todomvc

import org.w3c.dom.*
import kotlin.browser.document

fun main(args: Array<String>) {
    val todoRepository = TodoRepository()

    val elements = TodoMVCElements(
            todoRepository = todoRepository,
            main = document.querySelector(".main") as HTMLElement,
            footer = document.querySelector(".footer") as HTMLElement,
            newTodo = document.querySelector(".new-todo") as HTMLInputElement,
            todoList = document.querySelector(".todo-list") as HTMLUListElement,
            todoCount = document.querySelector(".todo-count") as HTMLSpanElement,
            clearCompleted = document.querySelector(".clear-completed") as HTMLButtonElement,
            toggleAll = document.querySelector(".toggle-all") as HTMLInputElement)

    todoRepository.addEmptyListener {
        elements.toggleMainAndFooter()
    }
}
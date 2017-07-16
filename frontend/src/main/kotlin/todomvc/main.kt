package todomvc

import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLUListElement
import org.w3c.dom.get
import kotlin.browser.document


fun main(args: Array<String>) {

    val todoRepository = TodoRepository()
    val elements = TodoMVCElements(
            todoRepository = todoRepository,
            main = document.getElementsByClassName("main")[0] as HTMLElement,
            footer = document.getElementsByClassName("footer")[0] as HTMLElement,
            newTodo = document.getElementsByClassName("new-todo")[0] as HTMLInputElement,
            todoList = document.getElementsByClassName("todo-list")[0] as HTMLUListElement)

//    elements.toggleMainAndFooter()
}
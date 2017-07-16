package todomvc

import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLLIElement
import org.w3c.dom.HTMLUListElement
import org.w3c.dom.events.KeyboardEvent
import kotlin.browser.document
import kotlin.dom.createElement

class TodoMVCElements(val todoRepository: TodoRepository,
                      val main: HTMLElement,
                      val footer: HTMLElement,
                      val newTodo: HTMLInputElement,
                      val todoList: HTMLUListElement) {

    var mainAndFooterVisible = true
    val visibleStates = mapOf(
            Pair(true, HTMLElement::hide),
            Pair(false, HTMLElement::show))

    init {
        newTodo.addEventListener("keypress", { e ->
            if (e is KeyboardEvent) {
                if (e.keyCode == 13) {
                    addNewTodo()
                }
            }
        }, false)
    }

    fun toggleMainAndFooter() {
        listOf(main, footer).forEach {
            visibleStates[mainAndFooterVisible]?.invoke(it)
        }
        mainAndFooterVisible = mainAndFooterVisible.not()
    }

    private fun addNewTodo() {
        val text = newTodo.innerText.trim()
        if (text.isNotBlank()) {
            todoRepository.addTodo(Todo("text"))
            newTodo.innerHTML = ""

        }
    }
}

fun HTMLElement.show() {
    this.style.display = "block"
}

fun HTMLElement.hide() {
    this.style.display = "none"
}

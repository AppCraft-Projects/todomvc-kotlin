package todomvc

import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.li
import org.w3c.dom.*
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent
import kotlin.browser.document
import kotlin.dom.addClass
import kotlin.dom.removeClass

class TodoMVCElements(val todoRepository: TodoRepository,
                      val main: HTMLElement,
                      val footer: HTMLElement,
                      val newTodo: HTMLInputElement,
                      val todoList: HTMLUListElement,
                      val todoCount: HTMLSpanElement,
                      val clearCompleted: HTMLButtonElement,
                      val toggleAll: HTMLInputElement) {

    var counter = 1
    var mainAndFooterVisible = true
    val visibleStates = mapOf(
            Pair(true, HTMLElement::hide),
            Pair(false, HTMLElement::show))

    init {
        toggleMainAndFooter()
        clearCompleted.hide()
        newTodo.addEventListener("keypress", { keyEvent ->
            if (keyEvent is KeyboardEvent) {
                if (keyEvent.keyCode == 13) {
                    addNewTodo(keyEvent)
                }
            }
        }, false)
        clearCompleted.addEventListener("click", {
            todoRepository.clearCompleted()
            updateFooter()
        }, false)
        toggleAll.addEventListener("click", {
            todoRepository.toggleCompletedOnAll()
            updateFooter()
        }, false)
    }

    fun toggleMainAndFooter() {
        listOf(main, footer).forEach {
            visibleStates[mainAndFooterVisible]?.invoke(it)
        }
        mainAndFooterVisible = mainAndFooterVisible.not()
    }

    private fun addNewTodo(e: KeyboardEvent) {
        val text = newTodo.value.trim()
        if (text.isNotBlank()) {
            val currCounter = counter
            val todoElement = createTodoListElement(text)
            val todo = Todo(
                    id = currCounter, // use it then increment it
                    task = text,
                    element = todoElement)

            val todoLabel = todoElement.querySelector("label") as HTMLLabelElement
            val todoEditInput = todoElement.querySelector(".edit") as HTMLInputElement
            val destroyButton = todoElement.querySelector(".destroy") as HTMLButtonElement
            val completedCheckbox = todoElement.querySelector(".toggle") as HTMLInputElement

            todoEditInput.addEventListener("keypress", { keyEvent ->
                if (keyEvent is KeyboardEvent) {
                    if (keyEvent.keyCode == 13) {
                        val newTodoText = todoEditInput.value
                        todoLabel.innerHTML = newTodoText
                        todo.task = newTodoText
                        todoElement.removeClass("editing")
                    }
                }
            }, false)

            todoLabel.addEventListener("dblclick", { e ->
                todoElement.addClass("editing")
                todoEditInput.value = todoLabel.innerHTML
                todoEditInput.focus()
            }, false)
            destroyButton.addEventListener("click", {
                todoRepository.deleteTodoById(currCounter)
                updateFooter()
            }, false)
            completedCheckbox.addEventListener("click", {
                todoRepository.fetchTodoById(currCounter)?.let { todo ->
                    completedCheckbox.let {
                        val checked = it.defaultChecked
                        it.checked = checked.not()
                        it.defaultChecked = checked.not()
                    }
                    todo.completed = todo.completed.not()
                    if (todo.completed) {
                        todo.element.addClass("completed")
                    } else {
                        todo.element.removeClass("completed")
                    }
                    updateFooter()
                }
            }, false)

            todoRepository.addTodo(todo)
            newTodo.value = ""
            todoList.appendChild(todoElement)
        }
        updateFooter()
        counter++
    }

    private fun updateFooter() {
        if (todoRepository.fetchCompletedCount() > 0) {
            clearCompleted.show()
        } else {
            clearCompleted.hide()
        }
        todoRepository.fetchTodoCount().minus(todoRepository.fetchCompletedCount()).let { remaining ->
            val itemStr = if (remaining == 1) "item" else "items"
            todoCount.innerHTML = "$remaining $itemStr left"
        }
    }

    private fun createTodoListElement(text: String): HTMLLIElement {
        val element = document.create.li("") {
            div("view") {
                input(type = InputType.checkBox, classes = "toggle") {}
                label { +text }
                button(classes = "destroy") { }
            }
            input(classes = "edit") { }
        }
        return element
    }
}


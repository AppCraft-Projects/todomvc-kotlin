package todomvc

import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.li
import org.w3c.dom.*
import org.w3c.dom.events.KeyboardEvent
import kotlin.browser.document
import kotlin.dom.addClass
import kotlin.dom.hasClass
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

    init {
        toggleMainAndFooterVisibility()
        clearCompleted.hide()
        newTodo.addEnterListener(this::addNewTodo)
        clearCompleted.addClickListener {
            todoRepository.clearCompleted()
            updateFooter()
        }
        toggleAll.addClickListener {
            todoRepository.toggleCompletedOnAll()
            updateFooter()
        }
    }

    fun toggleMainAndFooterVisibility() {
        main.toggleVisible()
        footer.toggleVisible()
    }

    private fun addNewTodo(e: KeyboardEvent) {
        val text = newTodo.getTrimmedValue()
        if (text.isNotBlank()) {
            val currCounter = counter
            val todoElement = createTodoListElement(text)
            val todo = Todo(
                    id = currCounter,
                    task = text,
                    element = todoElement)

            val todoLabel = todoElement.getChildBySelector<HTMLLabelElement>("label")
            val todoEditInput = todoElement.getChildBySelector<HTMLInputElement>(".edit")
            val destroyButton = todoElement.getChildBySelector<HTMLButtonElement>(".destroy")
            val completedCheckbox = todoElement.getChildBySelector<HTMLInputElement>(".toggle")

            todoEditInput.addBlurListener {
                if (todoElement.hasClass("editing")) {
                    finishEditing(todoEditInput, todoLabel, todo, todoElement)
                }
            }
            todoEditInput.addEnterListener {
                finishEditing(todoEditInput, todoLabel, todo, todoElement)
            }
            todoEditInput.addEscapeListener {
                todoEditInput.clearValue()
                todoElement.removeClass("editing")
            }
            todoLabel.addDoubleClickListener {
                todoElement.addClass("editing")
                todoEditInput.apply {
                    setValue(todoLabel.getText())
                    focus()
                }
            }
            destroyButton.addClickListener {
                todoRepository.deleteTodoById(currCounter)
                updateFooter()
            }
            completedCheckbox.addClickListener {
                completedCheckbox.toggle()
                todo.toggleCompleted()
                updateFooter()
            }

            todoRepository.addTodo(todo)
            newTodo.clearValue()
            todoList.appendChild(todoElement)
            updateFooter()
            counter++
        }
    }

    private fun finishEditing(todoEditInput: HTMLInputElement, todoLabel: HTMLLabelElement, todo: Todo, todoElement: HTMLLIElement) {
        val newTodoText = todoEditInput.getTrimmedValue()
        if (newTodoText.isNotBlank()) {
            todoLabel.setText(newTodoText)
            todo.task = newTodoText
        } else {
            todoRepository.deleteTodoById(todo.id)
            todoEditInput.clearValue()
            updateFooter()
        }
        todoElement.removeClass("editing")
    }

    private fun updateFooter() {
        if (todoRepository.fetchCompletedCount() > 0) {
            clearCompleted.show()
        } else {
            clearCompleted.hide()
        }
        todoRepository.fetchTodoCount().minus(todoRepository.fetchCompletedCount()).let { remaining ->
            val itemStr = if (remaining == 1) "item" else "items"
            todoCount.setText("$remaining $itemStr left")
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


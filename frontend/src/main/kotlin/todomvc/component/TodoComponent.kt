package todomvc.component

import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLLIElement
import org.w3c.dom.HTMLLabelElement
import todomvc.component.ComponentClasses.COMPLETED_CHECKBOX_SELECTOR
import todomvc.component.ComponentClasses.DESTROY_BUTTON_SELECTOR
import todomvc.component.ComponentClasses.TODO_COMPLETED_CLASS
import todomvc.component.ComponentClasses.TODO_EDITING_CLASS
import todomvc.component.ComponentClasses.TODO_EDIT_INPUT_SELECTOR
import todomvc.component.ComponentClasses.TODO_LABEL_SELECTOR
import todomvc.data.TodoEdit
import todomvc.event.EventBus
import todomvc.event.EventType.*
import todomvc.extension.*
import kotlin.dom.addClass
import kotlin.dom.hasClass
import kotlin.dom.removeClass

class TodoComponent(private val id: Int,
                    element: HTMLLIElement)
    : Component<HTMLLIElement>(element) {

    val todoLabel = getElement().getChildBySelector<HTMLLabelElement>(TODO_LABEL_SELECTOR)
    val todoEditInput = getElement().getChildBySelector<HTMLInputElement>(TODO_EDIT_INPUT_SELECTOR)
    val destroyButton = getElement().getChildBySelector<HTMLButtonElement>(DESTROY_BUTTON_SELECTOR)
    val completedCheckbox = getElement().getChildBySelector<HTMLInputElement>(COMPLETED_CHECKBOX_SELECTOR)

    init {
        todoEditInput.addBlurListener {
            if (getElement().hasClass(TODO_EDITING_CLASS)) {
                finishEditing()
            }
        }
        todoEditInput.addEnterListener {
            finishEditing()
        }
        todoEditInput.addEscapeListener {
            todoEditInput.clearValue()
            getElement().removeClass(TODO_EDITING_CLASS)
        }
        todoLabel.addDoubleClickListener {
            getElement().addClass(TODO_EDITING_CLASS)
            todoEditInput.apply {
                setValue(todoLabel.getText())
                focus()
            }
        }
        destroyButton.addClickListener {
            emitDeleteEvent()
        }
        completedCheckbox.addClickListener {
            toggleCompleted()
            EventBus.emit(COMPLETE_TODO, id)
        }
    }

    fun toggleCompleted() {
        getElement().let { todoElement ->
            if (todoElement.hasClass(TODO_COMPLETED_CLASS)) {
                todoElement.removeClass(TODO_COMPLETED_CLASS)
            } else {
                todoElement.addClass(TODO_COMPLETED_CLASS)
            }
            completedCheckbox.toggle()
        }
    }

    private fun finishEditing() {
        val newTodoText = todoEditInput.getTrimmedValue()
        if (newTodoText.isBlank()) {
            emitDeleteEvent()
            todoEditInput.clearValue()
        } else {
            todoLabel.setText(newTodoText)
            EventBus.emit(EDIT_TODO, TodoEdit(id, newTodoText))
        }
        getElement().removeClass(TODO_EDITING_CLASS)
    }

    private fun emitDeleteEvent() {
        EventBus.emit(DELETE_TODO, id)
    }
}
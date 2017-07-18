package todomvc.component

import org.w3c.dom.HTMLInputElement
import todomvc.component.ComponentClasses.NEW_TODO_INPUT_SELECTOR
import todomvc.event.EventBus
import todomvc.event.EventType.ADD_TODO
import todomvc.extension.addEnterListener
import todomvc.extension.clearValue
import todomvc.extension.fetchElementBySelector
import todomvc.extension.getTrimmedValue
import kotlin.browser.document

class NewTodoComponent
    : Component<HTMLInputElement>(document.fetchElementBySelector(NEW_TODO_INPUT_SELECTOR)) {

    init {
        getElement().let { newTodoElement ->
            newTodoElement.addEnterListener {
                newTodoElement.getTrimmedValue().let { text ->
                    if (text.isNotBlank()) {
                        EventBus.emit(ADD_TODO, text)
                        newTodoElement.clearValue()
                    }
                }
            }
        }
    }

}
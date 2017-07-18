package todomvc.component

import org.w3c.dom.HTMLButtonElement
import todomvc.component.ComponentClasses.CLEAR_COMPLETED_BUTTON_SELECTOR
import todomvc.data.TodoChange
import todomvc.event.EventBus
import todomvc.event.EventType.CLEAR_COMPLETED
import todomvc.event.EventType.TODO_CHANGED
import todomvc.extension.addClickListener
import todomvc.extension.fetchElementBySelector
import todomvc.extension.hide
import todomvc.extension.show
import kotlin.browser.document

class ClearCompletedComponent
    : Component<HTMLButtonElement>(document.fetchElementBySelector(CLEAR_COMPLETED_BUTTON_SELECTOR)) {

    init {
        getElement().let { clearCompletedButton ->
            clearCompletedButton.hide()
            clearCompletedButton.addClickListener {
                EventBus.emit(CLEAR_COMPLETED, Unit)
            }
            EventBus.subscribe<TodoChange>(TODO_CHANGED, { (todoChange) ->
                if (todoChange.completedCount > 0) {
                    clearCompletedButton.show()
                } else {
                    clearCompletedButton.hide()
                }
            })
        }
    }
}
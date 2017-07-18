package todomvc.component

import org.w3c.dom.HTMLSpanElement
import todomvc.component.ComponentClasses.TODO_COUNT_SELECTOR
import todomvc.data.TodoChange
import todomvc.event.EventBus
import todomvc.event.EventType.TODO_CHANGED
import todomvc.extension.fetchElementBySelector
import todomvc.extension.setText
import kotlin.browser.document

class TodoCountComponent
    : Component<HTMLSpanElement>(document.fetchElementBySelector(TODO_COUNT_SELECTOR)) {

    init {
        EventBus.subscribe<TodoChange>(TODO_CHANGED, { (todoChange) ->
            todoChange.remainingCount.let { remaining ->
                val itemStr = if (remaining == 1) "item" else "items"
                getElement().setText("$remaining $itemStr left")
            }
        })
    }
}
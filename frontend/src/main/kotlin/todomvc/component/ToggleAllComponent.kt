package todomvc.component

import org.w3c.dom.HTMLInputElement
import todomvc.component.ComponentClasses.TOGGLE_ALL_INPUT_SELECTOR
import todomvc.event.EventBus
import todomvc.event.EventType.TOGGLE_COMPLETED_ON_ALL
import todomvc.extension.addClickListener
import todomvc.extension.fetchElementBySelector
import kotlin.browser.document

class ToggleAllComponent
    : Component<HTMLInputElement>(document.fetchElementBySelector(TOGGLE_ALL_INPUT_SELECTOR)) {

    init {
        getElement().addClickListener {
            EventBus.emit(TOGGLE_COMPLETED_ON_ALL, Unit)
        }
    }
}
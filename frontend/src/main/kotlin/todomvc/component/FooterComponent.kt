package todomvc.component

import org.w3c.dom.HTMLElement
import todomvc.component.ComponentClasses.FOOTER_COMPONENT_SELECTOR
import todomvc.event.EventBus
import todomvc.event.EventType.TODOS_EMPTY_OR_NO_LONGER_EMPTY
import todomvc.extension.fetchElementBySelector
import todomvc.extension.toggleVisibility
import kotlin.browser.document

class FooterComponent
    : Component<HTMLElement>(document.fetchElementBySelector(FOOTER_COMPONENT_SELECTOR)) {

    init {
        getElement().toggleVisibility()
        EventBus.subscribe<Int>(TODOS_EMPTY_OR_NO_LONGER_EMPTY, {
            getElement().toggleVisibility()
        })
    }
}
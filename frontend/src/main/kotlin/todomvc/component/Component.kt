package todomvc.component

import org.w3c.dom.HTMLElement

abstract class Component<out T : HTMLElement>(private val element: T) {

    fun getElement() = element

    fun remove() = element.remove()
}
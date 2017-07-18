package todomvc

import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.li
import org.w3c.dom.HTMLLIElement
import kotlin.browser.document

object HtmlComponentFactory {

    fun createTodoListElement(text: String): HTMLLIElement {
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
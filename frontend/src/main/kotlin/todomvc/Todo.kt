package todomvc

import org.w3c.dom.HTMLElement
import kotlin.dom.addClass
import kotlin.dom.removeClass

data class Todo(val id: Int,
                val element: HTMLElement,
                var task: String,
                var completed: Boolean = false) {

    fun toggleCompleted() {
        completed = completed.not()
        if (completed) {
            element.addClass("completed")
        } else {
            element.removeClass("completed")
        }
    }
}
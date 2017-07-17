package todomvc

import org.w3c.dom.HTMLElement

data class Todo(val id: Int,
                val element: HTMLElement,
                var task: String,
                var completed: Boolean = false)
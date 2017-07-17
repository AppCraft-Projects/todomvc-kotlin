@file:Suppress("UNCHECKED_CAST")

package todomvc

import org.w3c.dom.HTMLElement

fun HTMLElement.show() {
    this.style.display = "block"
}

fun HTMLElement.hide() {
    this.style.display = "none"
}

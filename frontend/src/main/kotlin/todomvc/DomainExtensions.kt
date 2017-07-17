@file:Suppress("UNCHECKED_CAST")

package todomvc

import org.w3c.dom.Document
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent

// element extensions
fun HTMLElement.addClickListener(listener: (MouseEvent) -> Unit) {
    this.addEventListener("click", { e ->
        listener.invoke(e as MouseEvent)
    }, false)
}

fun HTMLElement.addDoubleClickListener(listener: (MouseEvent) -> Unit) {
    this.addEventListener("dblclick", { e ->
        listener.invoke(e as MouseEvent)
    }, false)
}

fun HTMLElement.toggleVisible() {
    if (this.style.display == "none") {
        this.style.display = "block"
    } else {
        this.style.display = "none"
    }
}

fun HTMLElement.show() {
    this.style.display = "block"
}

fun HTMLElement.hide() {
    this.style.display = "none"
}

fun <T : HTMLElement> HTMLElement.getChildBySelector(selector: String) = this.querySelector(selector) as T

fun HTMLElement.getText() = this.innerHTML

fun HTMLElement.setText(text: String) {
    this.innerHTML = text
}

// input extensions
fun HTMLInputElement.getTrimmedValue() = this.value.trim()

fun HTMLInputElement.setValue(value: String) {
    this.value = value
}

fun HTMLInputElement.clearValue() {
    this.value = ""
}

fun HTMLInputElement.addEscapeListener(listener: (KeyboardEvent) -> Unit) {
    this.addEventListener("keyup", { e -> // keypress does not work for Escape for some reason
        if (e is KeyboardEvent) {
            if (e.keyCode == 27) {
                listener.invoke(e)
            }
        }
    }, false)
}

fun HTMLInputElement.addEnterListener(listener: (KeyboardEvent) -> Unit) {
    this.addEventListener("keypress", { e ->
        if (e is KeyboardEvent) {
            if (e.keyCode == 13) {
                listener.invoke(e)
            }
        }
    }, false)
}

fun HTMLInputElement.addBlurListener(listener: (Event) -> Unit) {
    this.addEventListener("blur", { e ->
        listener.invoke(e)
    }, false)
}

fun HTMLInputElement.toggle() {
    val checked = this.defaultChecked
    this.checked = checked.not()
    this.defaultChecked = checked.not()
}

// document extensions
fun <T : HTMLElement> Document.fetchElementBySelector(selector: String): T {
    return this.querySelector(selector) as T // not pretty but useful
}

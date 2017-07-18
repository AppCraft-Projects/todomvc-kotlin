package todomvc.store

import org.w3c.dom.get
import org.w3c.dom.set
import todomvc.data.Todo
import kotlin.browser.window

object LocalTodoStorage : TodoStorage {
    private val storageKey = "todos-kotlin2js"
    private val storage = window.localStorage

    override fun create(todo: Todo) {
        storage[generateKeyFor(todo)] = JSON.stringify(todo)
    }

    override fun remove(todo: Todo) {
        storage.removeItem(generateKeyFor(todo))
    }

    override fun update(todo: Todo) {
        remove(todo)
        create(todo)
    }

    override fun loadTodos(): List<Todo> {
        return (0..storage.length - 1).map { idx ->
            storage.key(idx)!!
        }.filter { key ->
            key.startsWith(storageKey)
        }.map { validKey ->
            JSON.parse<Todo>(storage[validKey]!!)
        }
    }

    private fun generateKeyFor(todo: Todo) = "$storageKey-${todo.id}"
}
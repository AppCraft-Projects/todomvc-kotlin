package todomvc.store

import todomvc.data.Todo

interface TodoStorage {

    fun create(todo: Todo)

    fun remove(todo: Todo)

    fun update(todo: Todo)

    fun loadTodos(): List<Todo>
}
package todomvc.data

data class Todo(val id: Int,
                var title: String,
                var completed: Boolean = false) {

    fun toggleCompleted() {
        completed = completed.not()
    }
}
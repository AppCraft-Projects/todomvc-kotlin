package todomvc.data

data class Todo(val id: Int,
                var task: String,
                var completed: Boolean = false) {

    fun toggleCompleted() {
        completed = completed.not()
    }
}
package todomvc.data

data class TodoChange(val totalCount: Int,
                      val remainingCount: Int,
                      val completedCount: Int)
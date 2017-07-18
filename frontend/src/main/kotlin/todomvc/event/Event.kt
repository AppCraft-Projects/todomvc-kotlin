package todomvc.event

data class Event<out T>(val type: EventType, val data: T, val keys: Set<String>)
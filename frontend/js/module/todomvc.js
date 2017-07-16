define(['exports', 'kotlin'], function (_, Kotlin) {
  'use strict';
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var Pair = Kotlin.kotlin.Pair;
  var mapOf = Kotlin.kotlin.collections.mapOf_qfcya0$;
  function Todo(task, completed) {
    if (completed === void 0)
      completed = false;
    this.task = task;
    this.completed = completed;
  }
  Todo.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Todo',
    interfaces: []
  };
  Todo.prototype.component1 = function () {
    return this.task;
  };
  Todo.prototype.component2 = function () {
    return this.completed;
  };
  Todo.prototype.copy_ivxn3r$ = function (task, completed) {
    return new Todo(task === void 0 ? this.task : task, completed === void 0 ? this.completed : completed);
  };
  Todo.prototype.toString = function () {
    return 'Todo(task=' + Kotlin.toString(this.task) + (', completed=' + Kotlin.toString(this.completed)) + ')';
  };
  Todo.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.task) | 0;
    result = result * 31 + Kotlin.hashCode(this.completed) | 0;
    return result;
  };
  Todo.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.task, other.task) && Kotlin.equals(this.completed, other.completed)))));
  };
  function TodoMVCElements(todoRepository, main, footer, newTodo, todoList) {
    this.todoRepository = todoRepository;
    this.main = main;
    this.footer = footer;
    this.newTodo = newTodo;
    this.todoList = todoList;
    this.mainAndFooterVisible = true;
    this.visibleStates = mapOf([new Pair(true, Kotlin.getCallableRef('hide', function ($receiver) {
      return hide($receiver);
    })), new Pair(false, Kotlin.getCallableRef('show', function ($receiver) {
      return show($receiver);
    }))]);
    this.newTodo.addEventListener('keypress', TodoMVCElements_init$lambda(this), false);
  }
  TodoMVCElements.prototype.toggleMainAndFooter = function () {
    var tmp$;
    tmp$ = listOf([this.main, this.footer]).iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var tmp$_0;
      (tmp$_0 = this.visibleStates.get_11rb$(this.mainAndFooterVisible)) != null ? tmp$_0(element) : null;
    }
    this.mainAndFooterVisible = !this.mainAndFooterVisible;
  };
  TodoMVCElements.prototype.addNewTodo_0 = function () {
    var $receiver = this.newTodo.innerText;
    var tmp$;
    var text = Kotlin.kotlin.text.trim_gw00vp$(Kotlin.isCharSequence(tmp$ = $receiver) ? tmp$ : Kotlin.throwCCE()).toString();
    if (!Kotlin.kotlin.text.isBlank_gw00vp$(text)) {
      this.todoRepository.addTodo_2yaclc$(new Todo('text'));
      this.newTodo.innerHTML = '';
    }
  };
  function TodoMVCElements_init$lambda(this$TodoMVCElements) {
    return function (e) {
      if (Kotlin.isType(e, KeyboardEvent)) {
        if (e.keyCode === 13) {
          this$TodoMVCElements.addNewTodo_0();
        }
      }
    };
  }
  TodoMVCElements.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'TodoMVCElements',
    interfaces: []
  };
  function show($receiver) {
    $receiver.style.display = 'block';
  }
  function hide($receiver) {
    $receiver.style.display = 'none';
  }
  function TodoRepository(todos) {
    if (todos === void 0) {
      todos = Kotlin.kotlin.collections.ArrayList_init_ww73n8$();
    }
    this.todos_0 = todos;
  }
  TodoRepository.prototype.addTodo_2yaclc$ = function (todo) {
    this.todos_0.add_11rb$(todo);
  };
  TodoRepository.prototype.getTodoCount = function () {
    return this.todos_0.size;
  };
  TodoRepository.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'TodoRepository',
    interfaces: []
  };
  function main(args) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    var todoRepository = new TodoRepository();
    var elements = new TodoMVCElements(todoRepository, Kotlin.isType(tmp$ = document.getElementsByClassName('main')[0], HTMLElement) ? tmp$ : Kotlin.throwCCE(), Kotlin.isType(tmp$_0 = document.getElementsByClassName('footer')[0], HTMLElement) ? tmp$_0 : Kotlin.throwCCE(), Kotlin.isType(tmp$_1 = document.getElementsByClassName('new-todo')[0], HTMLInputElement) ? tmp$_1 : Kotlin.throwCCE(), Kotlin.isType(tmp$_2 = document.getElementsByClassName('todo-list')[0], HTMLUListElement) ? tmp$_2 : Kotlin.throwCCE());
  }
  var package$todomvc = _.todomvc || (_.todomvc = {});
  package$todomvc.Todo = Todo;
  package$todomvc.TodoMVCElements = TodoMVCElements;
  package$todomvc.show_y4uc6z$ = show;
  package$todomvc.hide_y4uc6z$ = hide;
  package$todomvc.TodoRepository = TodoRepository;
  package$todomvc.main_kand9s$ = main;
  main([]);
  Kotlin.defineModule('todomvc', _);
  return _;
});

//# sourceMappingURL=todomvc.js.map

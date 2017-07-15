requirejs.config({
    paths: {
        kotlin: 'module/kotlin',
        todomvc: 'module/todomvc'
    }
});

requirejs(["todomvc"], function (todomvc) {
});
requirejs.config({
    paths: {
        "kotlin": 'module/kotlin',
        "kotlinx-html-js": 'module/kotlinx-html-js',
        "todomvc": "module/todomvc"
    }
});

requirejs(["todomvc"], function (todomvc) {
});
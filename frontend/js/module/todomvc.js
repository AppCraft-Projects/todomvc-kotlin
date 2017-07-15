define(['exports', 'kotlin'], function (_, Kotlin) {
  'use strict';
  var println = Kotlin.kotlin.io.println_s8jyv4$;
  function main(args) {
    println('Hi, from kotlin2js!');
  }
  var package$todomvc = _.todomvc || (_.todomvc = {});
  package$todomvc.main_kand9s$ = main;
  main([]);
  Kotlin.defineModule('todomvc', _);
  return _;
});

//# sourceMappingURL=todomvc.js.map

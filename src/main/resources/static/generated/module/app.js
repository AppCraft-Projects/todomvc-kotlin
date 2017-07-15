define(['exports', 'kotlin'], function (_, Kotlin) {
  'use strict';
  var println = Kotlin.kotlin.io.println_s8jyv4$;
  function main(args) {
    println('Hi, from kotlin2js!');
  }
  var package$app = _.app || (_.app = {});
  package$app.main_kand9s$ = main;
  main([]);
  Kotlin.defineModule('app', _);
  return _;
});

//# sourceMappingURL=app.js.map

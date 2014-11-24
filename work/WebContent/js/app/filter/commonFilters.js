/**
 * Created by larry on 11/24/14.
 */
angular.module('mainApp').filter('nospace', function () {
    return function (value) {
        return (!value) ? '' : value.replace(/ /g, '');
    };
}).filter('uncapitalize', function() {
    return function(input, all) {
      return (!!input) ? input.replace(/([^\W_]+[^\s-]*) */g, function(txt){
          return txt.charAt(0).toLowerCase() + txt.substr(1);
      }) : '';
    }
});
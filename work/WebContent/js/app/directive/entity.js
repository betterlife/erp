var entityDirective = function ($compile, $http, $templateCache) {
    var templateLoader;
    console.log("Entity Detail");
    return {
        restrict: 'AE',
        replace: true,
        scope: {id: "=", entityType: "=", entity: "="},
        compile: function (tElement, attrs) {
            console.log(tElement);
            console.log(attrs);
            var tplURL = '/rest/form/' + attrs.entityType + '/detail/' + attrs.id;
            templateLoader = $http.get(tplURL, {cache: $templateCache})
                .success(function (html) {
                    tElement.html(html);
                });
            return function (scope, element, attrs) {
                templateLoader.then(function (templateText) {
                    element.html($compile(tElement.html())(scope));
                });
            };
        }
    }
};

entityDirective.$inject = ['$compile', '$http', '$templateCache'];
angular.module('mainApp').directive("entity", entityDirective);
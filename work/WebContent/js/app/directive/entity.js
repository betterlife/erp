var entityDirective = function ($compile, $http, $templateCache) {
    console.log("Entity Detail");
    var templateLoader;
    return {
        restrict: 'AE',
        replace: false,
        transclude: true,
        link: function (scope, element, attrs) {
            scope.$watch("activeRelatedEntity",function(newValue,oldValue) {
                if (scope.activeRelatedEntity != undefined) {
                    //This gets called when activeRelatedEntity changes.
                    var tplURL = '/rest/form/' + scope.activeRelatedEntityType +
                        '/relate/' + scope.activeRelatedEntity.id;
                    templateLoader = $http.get(tplURL, {cache: $templateCache})
                        .success(function (html) {
                            var find = 'entity.';
                            var re = new RegExp(find, 'g');
                            html = html.replace(re, "activeRelatedEntity.");
                            element.html(html);
                            $compile(element.contents())(scope);
                        }).error(function (data, status) {
                            console.error(data);
                            console.error(status);
                        });
                } else {
                    element.html("<div>关联对象取值为空</div>");
                    $compile(element.contents())(scope);
                }
            });
        }
    }
};

entityDirective.$inject = ['$compile', '$http', '$templateCache'];
angular.module('mainApp').directive("entity", entityDirective);
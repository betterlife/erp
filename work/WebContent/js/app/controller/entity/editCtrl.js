/**
 * Created by larry on 11/24/14.
 */

var editCtrl = function ($scope, $http, $location, loginService, $routeParams) {
    "use strict";
    $scope.entityType = $routeParams.entityType;
    $scope.id = $routeParams.id;
    $scope.entity = {};

    $scope.open = function ($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.opened = true;
    };

    $scope.update = function () {
        $http.put("/rest/" + $scope.entityType + "/" + $scope.id, {
            'entity': $scope.entity
        }, {}).success(function (data) {
            console.log(data);
            $location.path("/" + $scope.entityType + "/list");
        }).error(function (data, status) {
        });
    };
};

editCtrl.$inject = ['$scope', '$http', '$location', 'loginService', '$routeParams'];

angular.module('mainApp').controller('editCtrl', editCtrl);

/**
 * Created by larry on 11/24/14.
 */

var editCtrl = function ($scope, $http, $location, loginService, $routeParams) {
    "use strict";
    $scope.entityType = $routeParams.entityType;
    $scope.captalizedEntityType = $scope.entityType.charAt(0).toUpperCase() + $scope.entityType.substr(1);
    $scope.id = $routeParams.id;
    $scope.entity = {};

    $scope.open = function ($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.opened = true;
    };

    $scope.reset = function () {
        $scope.entity = angular.copy($scope.originalEntity);
    };

    $http.get("/rest/" + $scope.captalizedEntityType + "/" + $scope.id, {}).success(function (data) {
        console.log(data);
        $scope.entity = data.result;
        $scope.originalEntity = angular.copy(data.result);
    });

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

/**
 * Created by larry on 11/24/14.
 */

var detailCtrl = function ($scope, $http, $location, loginService, $routeParams) {
    "use strict";
    $scope.entityType = $routeParams.entityType;
    $scope.captalizedEntityType = $scope.entityType.charAt(0).toUpperCase() + $scope.entityType.substr(1);
    $scope.id = $routeParams.id;
    $scope.entity = {};

    $http.get("/rest/" + $scope.captalizedEntityType + "/" + $scope.id, {}).success(function (data) {
        $scope.entity = data.result;
    }).error(function (data, status) {
        console.error(data);
        console.error(status);
    });
};

detailCtrl.$inject = ['$scope', '$http', '$location', 'loginService', '$routeParams'];

angular.module('mainApp').controller('detailCtrl', detailCtrl);

/**
 * Created by larry on 11/12/14.
 */

var createCtrl = function ($scope, $http, $location, loginService, $routeParams) {
    "use strict";
    $scope.entityType = $routeParams.entityType;
    $scope.captalizedEntityType = $scope.entityType.charAt(0).toUpperCase() + $scope.entityType.substr(1);
    $scope.entity = {
    };

    $scope.reset = function(){
        $scope.entity = {};
    };

    $scope.open = function($event) {
      $event.preventDefault();
      $event.stopPropagation();
      $scope.opened = true;
    };

    $scope.create = function () {
        $http.post("/rest/" + $scope.captalizedEntityType, {
            'entity' : $scope.entity
        },{}).success(function (data) {
            console.log(data);
            $location.path("/" + $scope.entityType + "/list");
        }).error(function (data, status) {
        });
    };

};

createCtrl.$inject = ['$scope', '$http', '$location', 'loginService', '$routeParams'];

angular.module('mainApp').controller('createCtrl', createCtrl);
/**
 * Created by larry on 11/12/14.
 */

var createCtrl = function ($scope, $http, $location, loginService, typeHeadService, $routeParams) {
    "use strict";
    $scope.entityType = $routeParams.entityType;
    $scope.captalizedEntityType = $scope.entityType.charAt(0).toUpperCase() + $scope.entityType.substr(1);
    $scope.entity = {
    };

    $scope.reset = function(){
        $scope.entity = {};
    };

    $scope.calendar = {
        opened: {},
        open: function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.calendar.opened[which] = true;
        }
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

    $scope.refreshOptions = function(fieldEntityType, representField, fieldName) {
        typeHeadService.refreshOptions(fieldEntityType, representField, fieldName, '? undefined:undefined ?');
    };

    $scope.onTypeHeadSelect = function($item, $model, $label, baseObjectFieldName) {
        $scope.entity[baseObjectFieldName] = typeHeadService.onTypeHeadSelect($item, $model, $label, baseObjectFieldName);
    };

    $scope.getBaseObjects = function(entityType, representField, val) {
        return typeHeadService.getBaseObjects(entityType, representField, val);
    };

};

createCtrl.$inject = ['$scope', '$http', '$location', 'loginService', 'typeHeadService', '$routeParams'];

angular.module('mainApp').controller('createCtrl', createCtrl);
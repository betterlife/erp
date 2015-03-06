/**
 * Created by larry on 11/24/14.
 */

var editCtrl = function ($scope, $http, $location, loginService, typeHeadService, $routeParams) {
    "use strict";
    $scope.entityType = $routeParams.entityType;
    $scope.captalizedEntityType = $scope.entityType.charAt(0).toUpperCase() + $scope.entityType.substr(1);
    $scope.id = $routeParams.id;
    $scope.entity = {};
    $scope.calendar = {
        opened: {},
        open: function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.calendar.opened[which] = true;
        }
    };

    $scope.reset = function () {
        $scope.entity = angular.copy($scope.originalEntity);
    };

    $http.get("/rest/" + $scope.captalizedEntityType + "/" + $scope.id, {}).success(function (data) {
        $scope.entity = data.result;
        $scope.originalEntity = angular.copy(data.result);
    });

    $scope.refreshOptions = function(fieldEntityType, representField, fieldName) {
        typeHeadService.refreshOptions(fieldEntityType, representField, fieldName, $scope.entity[fieldName].id);
    };

    $scope.update = function () {
        $http.put("/rest/" + $scope.entityType + "/" + $scope.id, {
            'entity': $scope.entity
        }, {}).success(function (data) {
            $location.path("/" + $scope.entityType + "/list");
        }).error(function (data, status) {
        });
    };

    $scope.onTypeHeadSelect = function($item, $model, $label, baseObjectFieldName) {
        $scope.entity[baseObjectFieldName] = typeHeadService.onTypeHeadSelect($item, $model, $label, baseObjectFieldName);
    };

    $scope.getAllBaseObjects = function (entityType, representField, name) {
        /**
        var defer = $q.defer();
        $http.get('/rest/' + entityType, {}).then(function (response) {
            console.log(response);
            defer.resolve(response);
        });
        var message = defer.promise;
        console.log(message);*/
        return [{id: 1, description: "abc"}, {id: 2, description: "def"}];
    };

    $scope.getBaseObjects = function(entityType, representField, val) {
        return typeHeadService.getBaseObjects(entityType, representField, val);
    };
};

editCtrl.$inject = ['$scope', '$http', '$location', 'loginService', 'typeHeadService', '$routeParams'];

angular.module('mainApp').controller('editCtrl', editCtrl);

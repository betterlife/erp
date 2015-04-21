/**
 * Author: Lawrence Liu
 * Date: 11/24/14
 */

var detailCtrl = function ($scope, $http, $location, $sce, loginService, $routeParams) {
    "use strict";
    $scope.entityType = $routeParams.entityType;
    $scope.captalizedEntityType = $scope.entityType.charAt(0).toUpperCase() + $scope.entityType.substr(1);
    $scope.id = $routeParams.id;
    $scope.entity = {};
    $scope.gridOptions = {};

    $http.get("/rest/" + $scope.captalizedEntityType + "/" + $scope.id, {}).success(function (data) {
        $scope.entity = data.result;
    }).error(function (data, status) {
        console.error(data);
        console.error(status);
    });

    $scope.getRelatedEntity = function (name, entityType) {
        $scope.activeRelatedEntity = $scope.entity[name];
        $scope.activeRelatedEntityIsArray = Array.isArray($scope.activeRelatedEntity);
        $scope.activeRelatedEntityTab = name;
        var relatedId = $scope.activeRelatedEntity.id;
        if ($scope.activeRelatedEntityIsArray && $scope.activeRelatedEntity.length > 0) {
            $scope.relatedEntityDetailInfo = $sce.trustAsHtml("");
            $scope.gridOptions = {
                enableSorting: true,
                enableColumnResizing: true,
                enableGridMenu: true,
                showColumnFooter: true,
                enablePaginationControls: true,
                enableHorizontalScrollbar: 1,
                enableVerticalScrollbar: 1,
                paginationPageSizes: [10, 20, 40, 80],
                paginationPageSize: 18,
                data : $scope.activeRelatedEntity
            };
            var firstObj = $scope.activeRelatedEntity[0];
            $scope.gridOptions.columnDefs = [];
            for (var prop in firstObj) {
                if (firstObj.hasOwnProperty(prop)) {
                    $scope.gridOptions.columnDefs.push(
                        {"field": prop, "name": prop}
                    );
                }
            }
        } else {
            var formUrl = '/rest/form/' + entityType + '/relate/' + relatedId;
            $http.get(formUrl, {}).success(function (data) {
                $scope.relatedEntityDetailInfo = $sce.trustAsHtml(data);
            }).error(function (data, status) {
                console.error(data);
                console.error(status);
            });
        }
    };
};

detailCtrl.$inject = ['$scope', '$http', '$location', '$sce', 'loginService', '$routeParams'];

angular.module('mainApp').controller('detailCtrl', detailCtrl);

/**
 *         enableSorting: true,
 columnDefs: [
            {name: 'firstName', field: 'first-name'},
            {name: '1stFriend', field: 'friends[0]'},
            {name: 'city', field: 'address.city'},
            {name: 'getZip', field: 'getZip()', enableCellEdit: false}
        ],
        data: [{
            "first-name": "Cox",
            "friends": ["friend0"],
            "address": {street: "301 Dove Ave", city: "Laurel", zip: "39565"},
            "getZip": function () {
                return this.address.zip;
            }
        }
        ]
 */
var listCtrl = function ($scope, $http, $location, loginService, $routeParams) {
    "use strict";
    $scope.entityType = $routeParams.entityType;
    $scope.captalizedEntityType = $scope.entityType.charAt(0).toUpperCase() + $scope.entityType.substr(1);
    $scope.gridOptions = {
        enableSorting: true,
        enableColumnResizing: true,
        enableGridMenu: true
    };
    $http.get("/rest/entity/" + $scope.captalizedEntityType, {}).success(function (metaData) {
        $scope.gridOptions.columnDefs = metaData.result;
        $scope.gridOptions.columnDefs.push({
            name : "operation", displayName : "Operation", enableCellEdit : false,
            cellTemplate : '<div class="ui-grid-cell-contents"><a href="/' + $scope.entityType + '/edit/{{row.entity.id}}" class="glyphicon glyphicon-edit"></a></div>'
        });
        $http.get("/rest/" + $scope.captalizedEntityType, {}).success(function (entityData) {
            $scope.gridOptions.data = entityData.result;
        })
    });
};

listCtrl.$inject = ['$scope', '$http', '$location', 'loginService', '$routeParams'];

angular.module('mainApp').controller('listCtrl', listCtrl);

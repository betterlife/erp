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
var listCtrl = function ($scope, $http, $location, loginService, i18nService, $routeParams) {
    "use strict";
    i18nService.setCurrentLang('zh-cn');
    $scope.entityType = $routeParams.entityType;
    $scope.captalizedEntityType = $scope.entityType.charAt(0).toUpperCase() + $scope.entityType.substr(1);
    $scope.externalScope = $scope;
    $scope.gridOptions = {
        enableSorting: true,
        enableColumnResizing: true,
        enableGridMenu: true,
        showFooter: true
    };

    $http.get("/rest/entity/" + $scope.captalizedEntityType, {}).success(function (metaData) {
        $scope.gridOptions.columnDefs = metaData.result;
        $scope.gridOptions.columnDefs.push({
            name : "operation", displayName : "操作", enableCellEdit : false,
            cellTemplate : '<div class="ui-grid-cell-contents">' +
            '<a href="/' + $scope.entityType + '/edit/{{row.entity.id}}" class="glyphicon glyphicon-edit"></a>' +
            '<span style="padding-left: 10px;cursor: hand"><a ng-click="getExternalScopes().deActive(row)" href="#" class="glyphicon glyphicon-remove"></a></span>' +
            '</div>'
        });
        $http.get("/rest/" + $scope.captalizedEntityType, {}).success(function (entityData) {
            $scope.gridOptions.data = entityData.result;
        })
    });

    $scope.myViewModel = {
        deActive: function (row) {
            var id = row.entity.id;
            var entity = {
                'id': id,
                'active': false
            };
            var index = $scope.gridOptions.data.indexOf(row.entity);
            $http.put("/rest/" + $scope.captalizedEntityType  + "/" + id, {
                'entity': entity
            }, {}).success(function (data) {
                $scope.gridOptions.data.splice(index, 1);
            }).error(function (data, status) {
            });
        }
    };
};

listCtrl.$inject = ['$scope', '$http', '$location', 'loginService', 'i18nService', '$routeParams'];

angular.module('mainApp').controller('listCtrl', listCtrl);

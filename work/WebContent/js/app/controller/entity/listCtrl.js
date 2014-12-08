/**
 *         enableSorting: true,
 columnDefs: [
             {"field":"id","name":"Id"},
             {"field":"lastModifyDate","name":"LastModifyDate"},
             {"field":"lastModify","name":"LastModify"},
             {"field":"createDate","name":"CreateDate"},
             {"field":"name","name":"Name"},
             {"field":"creator","name":"Creator"}
         ],
         data: [
             {"name":null,"id":1,"lastModifyDate":1417839853680,"lastModify":null,"createDate":1417839853680,"creator":null},
             {"name":null,"id":2,"lastModifyDate":1417840072000,"lastModify":null,"createDate":1417840072000,"creator":null},
             {"name":null,"id":3,"lastModifyDate":1417840272780,"lastModify":null,"createDate":1417840272780,"creator":null},
             {"name":null,"id":4,"lastModifyDate":1417840302970,"lastModify":null,"createDate":1417840302970,"creator":null},
             {"name":null,"id":5,"lastModifyDate":1417840345540,"lastModify":null,"createDate":1417840345540,"creator":null},
             {"name":null,"id":6,"lastModifyDate":1417840487580,"lastModify":null,"createDate":1417840487580,"creator":null},
             {"name":null,"id":7,"lastModifyDate":1417840699440,"lastModify":null,"createDate":1417840699440,"creator":null},
             {"name":null,"id":8,"lastModifyDate":1417868585900,"lastModify":null,"createDate":1417868585900,"creator":null},
             {"name":"市场部","id":9,"lastModifyDate":1417869186200,"lastModify":null,"createDate":1417869186200,"creator":null}
         ]
 */
var listCtrl = function ($scope, $http, $location, loginService, $routeParams) {
    "use strict";
    $scope.entityType = $routeParams.entityType;
    $scope.captalizedEntityType = $scope.entityType.charAt(0).toUpperCase() + $scope.entityType.substr(1);
    $scope.gridOptions = {
        enableSorting: true
    };
    $http.get("/rest/entity/" + $scope.captalizedEntityType, {}).success(function (metaData) {
        $scope.gridOptions.columnDefs = metaData.result;
        $http.get("/rest/" + $scope.captalizedEntityType, {}).success(function (entityData) {
            $scope.gridOptions.data = entityData.result;
        })
    });
};

listCtrl.$inject = ['$scope', '$http', '$location', 'loginService', '$routeParams'];

angular.module('mainApp').controller('listCtrl', listCtrl);

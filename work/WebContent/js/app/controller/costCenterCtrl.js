/**
 * Created by larry on 11/24/14.
 */

var costCenterCtrl = function ($scope, $http, $location, loginService, $routeParams) {
    "use strict";
    $scope.operation = $routeParams.operation;

    $scope.create = function () {
        $http.post("/rest/costCenter", {
            "categoryName": $scope.categoryName
        }, {}).success(function (data) {
            console.log(data);
            $location.path("/costCenter/list");
        }).error(function (data, status) {
        });
    };

    $scope.list = function () {
        $http.get("/rest/costCenter").success(function (data){
            console.log(data);
            $scope.data = data.result;
        }).error(function(data, status){

        });
    };

    if ($scope.operation == 'list') {
        $scope.list();
    }

    $scope.isInModel = function(expectModel) {
        return $scope.operation == expectModel;
    }
};

costCenterCtrl.routerConfig = {
    templateUrl: '/templates/entity/costCenter.html',
    controller: costCenterCtrl,
    authenticate: true
};

userCtrl.$inject = ['$scope', '$http', '$location', 'loginService', '$routeParams'];

angular.module('mainApp').controller('costCenterCtrl', costCenterCtrl);

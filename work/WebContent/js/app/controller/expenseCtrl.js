/**
 * Created by larry on 11/24/14.
 */

var expenseCtrl = function ($scope, $http, $location, loginService, $routeParams) {
    "use strict";
    $scope.operation = $routeParams.operation;

    $scope.create = function () {
        $http.post("/rest/expense", {
            "categoryName": $scope.categoryName
        }, {}).success(function (data) {
            console.log(data);
            $location.path("/expense/list");
        }).error(function (data, status) {
        });
    };

    $scope.list = function () {
        $http.get("/rest/expense").success(function (data){
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

expenseCtrl.routerConfig = {
    templateUrl: '/templates/entity/expense.html',
    controller: expenseCtrl,
    authenticate: true
};

userCtrl.$inject = ['$scope', '$http', '$location', 'loginService', '$routeParams'];

angular.module('mainApp').controller('expenseCtrl', expenseCtrl);

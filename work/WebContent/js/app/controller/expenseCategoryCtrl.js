/**
 * Created by larry on 11/24/14.
 */

var expenseCategoryCtrl = function ($scope, $http, $location, loginService, $routeParams) {
    "use strict";
    $scope.operation = $routeParams.operation;

    $scope.create = function () {
        $http.post("/rest/expenseCategory", {
            "categoryName": $scope.categoryName
        }, {}).success(function (data) {
            console.log(data);
            $location.path("/expenseCategory/list");
        }).error(function (data, status) {
        });
    };

    $scope.list = function () {
        $http.get("/rest/expenseCategory").success(function (data){
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

expenseCategoryCtrl.routerConfig = {
    templateUrl: '/templates/entity/expenseCategory.html',
    controller: expenseCategoryCtrl,
    authenticate: true
};

userCtrl.$inject = ['$scope', '$http', '$location', 'loginService', '$routeParams'];

angular.module('mainApp').controller('expenseCategoryCtrl', expenseCategoryCtrl);

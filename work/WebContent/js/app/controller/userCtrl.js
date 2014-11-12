/**
 * Created by larry on 11/12/14.
 */

var userCtrl = function ($scope, $http, $location, loginService, $routeParams) {
    "use strict";
    $scope.operation = $routeParams.operation;

    $scope.create = function () {
        $http.post("/rest/user", {
            "username": $scope.username,
            "password": $scope.password
        }, {}).success(function (data) {
            console.log(data);
            $location.path("/user/list");
        }).error(function (data, status) {
        });
    };

    $scope.list = function () {
        $http.get("/rest/user").success(function (data){
            console.log(data);
            $scope.users = data.result;
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

userCtrl.routerConfig = {
    templateUrl: '/templates/entity/user.html',
    controller: userCtrl,
    authenticate: true
};

userCtrl.$inject = ['$scope', '$http', '$location', 'loginService', '$routeParams'];

angular.module('mainApp').controller('userCtrl', userCtrl);
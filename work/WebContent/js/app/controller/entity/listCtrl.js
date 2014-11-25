/**
 * Created by larry on 11/24/14.
 */

var listCtrl = function ($scope, $http, $location, loginService, $routeParams) {
    "use strict";
    $scope.entityType = $routeParams.entityType;
};

listCtrl.$inject = ['$scope', '$http', '$location', 'loginService', '$routeParams'];

angular.module('mainApp').controller('listCtrl', listCtrl);

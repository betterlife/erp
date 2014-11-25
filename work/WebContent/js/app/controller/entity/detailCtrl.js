/**
 * Created by larry on 11/24/14.
 */

var detailCtrl = function ($scope, $http, $location, loginService, $routeParams) {
    "use strict";
    $scope.entityType = $routeParams.entityType;
    $scope.id = $routeParams.id;
};

detailCtrl.$inject = ['$scope', '$http', '$location', 'loginService', '$routeParams'];

angular.module('mainApp').controller('detailCtrl', detailCtrl);

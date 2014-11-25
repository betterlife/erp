/**
 * Created by larry on 11/24/14.
 */

var editCtrl = function ($scope, $http, $location, loginService, $routeParams) {
    "use strict";
    $scope.entityType = $routeParams.entityType;
};

editCtrl.$inject = ['$scope', '$http', '$location', 'loginService', '$routeParams'];

angular.module('mainApp').controller('editCtrl', editCtrl);

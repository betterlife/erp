var dashboardCtrl = function ($scope, $http, loginService, $location, $routeParams) {
    "use strict";
    console.log("Dashboard Ctrl Invoked");
    if (!loginService.isLoggedIn()) {
        $location.path("/login.html");
    }
};

dashboardCtrl.$inject = ['$scope', '$http', 'loginService', '$location', '$routeParams'];

dashboardCtrl.routerConfig = {
    templateUrl: '/templates/dashboard.html',
    controller: dashboardCtrl,
    authenticate: true
};

angular.module('mainApp').controller('dashboardCtrl', dashboardCtrl);

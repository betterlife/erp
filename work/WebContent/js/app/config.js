// Declare app level module which depends on filters, and services
(function () {
    "use strict";
    console.log("Init angular module");
    angular.module('mainApp', ['ngRoute', 'ngCookies'])
        .filter('to_trusted', ['$sce', function ($sce) {
            return function (text) {
                return $sce.trustAsHtml(text);
            };
        }])
        .config(['$routeProvider', '$locationProvider',
            function ($routeProvider, $locationProvider) {
                console.log("Config route provider");
                console.log($routeProvider);
                console.log(dashboardCtrl.routerConfig);
                $routeProvider
                    .when('/', dashboardCtrl.routerConfig)
                    .when('/dashboard', dashboardCtrl.routerConfig)
                    .when('/login', loginCtrl.routerConfig)
                    .when('/logout', logoutCtrl.routerConfig)
                    .otherwise({redirectTo: '/login'});
                $locationProvider.html5Mode(false);
            }])
        .run(['$rootScope', '$location', 'loginService',
            function ($rootScope, $location, loginService) {
                // Redirect to login if route requires auth and you're not logged in
                console.log("Register the routeChange start event");
                console.log("current $location.path is: %s", $location.path());
                $rootScope.$on('$routeChangeStart', function (event, next) {
                    console.log("Checking for login: %s", loginService.isLoggedIn());
                    if (next.authenticate && !loginService.isLoggedIn()) {
                        $location.path('/login');
                    }
                });
            }])
        .config(['$locationProvider', function ($locationProvider) {
            $locationProvider.html5Mode(true);
        }]);
})();

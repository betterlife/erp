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
        .controller('mainController', function ($scope, $rootScope, $http, $location,
                                                loginService, $route, $routeParams) {
            $scope.$route = $route;
            $scope.$location = $location;
            $scope.$routeParams = $routeParams;

            $scope.isActive = function (route) {
                return route === $location.path();
            };

            $scope.isLoggedIn = function () {
                return loginService.isLoggedIn();
            }
        })
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
                    .when('/user/:operation', userCtrl.routerConfig)
                    .otherwise({redirectTo: '/login'});
                $locationProvider.html5Mode(false);
            }])
        .run(['$rootScope', '$location', 'loginService',
            function ($rootScope, $location, loginService) {
                console.info("Register the routeChange start event");
                console.debug("current $location.path is: %s", $location.path());
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

// Declare app level module which depends on filters, and services
(function () {
    "use strict";
    angular.module('mainApp', ['ngRoute', 'ngCookies', 'ui.bootstrap', 'ui.grid','ui.grid.resizeColumns'])
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
                $routeProvider
                    .when('/', dashboardCtrl.routerConfig)
                    .when('/dashboard', dashboardCtrl.routerConfig)
                    .when('/login', loginCtrl.routerConfig)
                    .when('/logout', logoutCtrl.routerConfig)
                    .when('/:entityType/create', {
                        templateUrl: function ($routeParams){
                            return '/rest/form/' + $routeParams.entityType + '/create';
                        },
                        controller: createCtrl,
                        authenticate: true
                    })
                    .when('/:entityType/edit', {
                        templateUrl: function ($routeParams){
                            return '/rest/form/' + $routeParams.entityType + '/edit';
                        },
                        controller: editCtrl,
                        authenticate: true
                    })
                    .when('/:entityType/list', {
                        templateUrl: function ($routeParams){
                            return '/rest/form/' + $routeParams.entityType + '/list';
                        },
                        controller: listCtrl,
                        authenticate: true
                    })
                    .when('/:entityType/detail/:id',{
                        templateUrl: function ($routeParams){
                            return '/rest/form/' + $routeParams.entityType + '/detail';
                        },
                        controller: detailCtrl,
                        authenticate: true
                    })
                    .otherwise({redirectTo: '/login'});
                $locationProvider.html5Mode(false);
            }])
        .run(['$rootScope', '$location', 'loginService',
            function ($rootScope, $location, loginService) {
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

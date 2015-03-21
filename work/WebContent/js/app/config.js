// Declare app level module which depends on filters, and services
(function () {
    "use strict";
    angular.module('mainApp', ['ngRoute', 'ngCookies', 'ui.bootstrap', 'ui.bootstrap.typeahead', 'ui.grid',
        'ui.grid.expandable', 'ui.grid.resizeColumns', 'ui.grid.pagination'])
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
                return $location.path().indexOf(route) >= 0;
            };

            $scope.isLoggedIn = function () {
                return loginService.isLoggedIn();
            };
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
                    .when('/:entityType/edit/:id', {
                        templateUrl: function ($routeParams){
                            return '/rest/form/' + $routeParams.entityType + '/edit/' + $routeParams.id;
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
                            return '/rest/form/' + $routeParams.entityType + '/detail/' + $routeParams.id;
                        },
                        controller: detailCtrl,
                        authenticate: true
                    })
                    .otherwise({redirectTo: '/login'});
                $locationProvider.html5Mode(false);
            }])
        .run(['$rootScope', '$location', 'loginService',
            function ($rootScope, $location, loginService) {
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

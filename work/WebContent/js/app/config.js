// Declare app level module which depends on filters, and services
(function () {
    "use strict";
    angular.module('mainApp', ['ngCookies'])
        .filter('to_trusted', ['$sce', function ($sce) {
            return function (text) {
                return $sce.trustAsHtml(text);
            };
        }])
        .config(['$locationProvider', function ($locationProvider) {
            $locationProvider.html5Mode(true);
        }]);
})();

var loginService = function ($rootScope, $http, $cookieStore, $location) {
    "use strict";
    var internal = {};
    internal.user = null;
    internal.login = function (username, password) {
        $http.post('/rest/login', {}, {
            params: {
                "username": username, "password": password
            }
        }).success(function (data) {
            $location.path('/');
            $rootScope.errors = {};
            internal.user = data.user;
            if (internal.user === null) {
                $cookieStore.remove('user');
            }
            else {
                $cookieStore.put('user', internal.user);
            }
        }).error(function (data, status) {
            $rootScope.login_error = '登录失败, 请检查用户名密码, 或者点这里<a class="alert-link" href="mailto:helpdesk@betterlife.io">报告登陆问题</a>';
        });
    };

    internal.getLoggedInUser = function () {
        return internal.user;
    };

    internal.isLoggedIn = function () {
        if (internal.user === null || internal.user === undefined) {
            internal.user = $cookieStore.get('user');
        }
        return (internal.user !== null && internal.user !== undefined);
    };

    internal.logout = function () {
        //Clear of cookie and internal data should be synchronized to
        //update menu on the fly
        $cookieStore.remove('user');
        internal.user = undefined;
        $location.path('/login');
        $http.get('/rest/logout').success(
            function (data, status, headers, config) {
                //Do nothing here
            }).error(function (data, status) {
                //Do nothing here
            });
    };

    return internal;
};

loginService.$inject = ['$rootScope', '$http', '$cookieStore', '$location'];

angular.module('mainApp').factory('loginService', loginService);

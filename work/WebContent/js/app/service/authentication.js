var loginService = function ($rootScope, $http, $cookieStore, $location) {
    "use strict";
    var internal = {};
    internal.user = null;
    internal.login = function (username, password, loginCallback) {
        //TODO 修改为POST请求的形式
        $http.post('/rest/login/' + username + '/' + password)
            .success(function (data) {
                $rootScope.login_errors = {};
                internal.user = data.result;
                if (internal.user === null) {
                    $cookieStore.remove('user');
                }
                else {
                    $cookieStore.put('user', internal.user);
                }
                loginCallback(data.user);
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
        var removed_user = internal.user;
        internal.user = undefined;
        $location.path('/login');
        $http.get('/rest/logout').success(
            function (data, status, headers, config) {
                console.log("successful logout user %s", removed_user);
            }).error(function (data, status) {
                console.log("error during logout user %s: %s, %s", removed_user, data, status);
            });
    };

    return internal;
};

loginService.$inject = ['$rootScope', '$http', '$cookieStore', '$location'];

angular.module('mainApp').factory('loginService', loginService);

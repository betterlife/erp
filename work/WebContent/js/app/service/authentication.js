var loginService = function ($rootScope, $http, $cookieStore, $location) {
    "use strict";
    var internal = {};
    internal.user = null;
    internal.login = function (username, password, loginCallback) {
        //TODO 修改为POST请求的形式
        $http.post('/rest/security/login/' + username + '/' + password, {}, {})
            .success(function (data) {
                internal.user = data.result;
                if (internal.user === null) {
                    $cookieStore.remove('user');
                }
                else {
                    $cookieStore.put('user', internal.user);
                }
                $rootScope.login_errors = '';
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
        $http.post('/rest/security/logout/' + removed_user.username, {}, {})
            .success(function (data, status, headers, config) {
                console.info("successful logout user %s", removed_user);
                $location.path('/login');
            }).error(function (data, status) {
                console.error("error during logout user %s: %s, %s", removed_user, data, status);
                $location.path('/login');
            });
    };

    return internal;
};

loginService.$inject = ['$rootScope', '$http', '$cookieStore', '$location'];

angular.module('mainApp').factory('loginService', loginService);

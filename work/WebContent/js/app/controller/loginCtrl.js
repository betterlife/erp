var loginCtrl = function ($scope, $http, loginService, $location, $routeParams) {
    "use strict";
    console.log("Login Ctrl Invoked");

    var redirect = function($location) {
        $location.path('/dashboard');
    };

    $scope.login = function () {
        var username = $scope.username,
            password = $scope.password;
        if(loginService.isLoggedIn()) {
            redirect($location);
            return;
        }
        if (username !== undefined && username !== '' &&
            password !== undefined && password !== '') {
            loginService.login(username, password, function(user){
                redirect($location);
            });
        } else {
            $scope.login_error = "请输入用户名密码";
        }
    };
};

loginCtrl.$inject = ['$scope', '$http', 'loginService', '$location', '$routeParams'];

loginCtrl.routerConfig = {
    templateUrl: '/templates/login.html',
    controller: loginCtrl
};

var logoutCtrl = function ($scope, $http, $location, loginService, $routeParams) {
    "use strict";
    loginService.logout();
};

logoutCtrl.$inject = ['$scope', '$http', '$location', 'loginService', '$routeParams'];

logoutCtrl.routerConfig = {
    templateUrl: '/templates/login.html',
    controller: logoutCtrl,
    authenticate: true
};

angular.module('mainApp').controller('loginCtrl', loginCtrl);
angular.module('mainApp').controller('logoutCtrl', logoutCtrl);

var loginCtrl = function ($scope, $http, loginService, $location) {
    "use strict";
    console.log("Login Ctrl Invoked");
    $scope.login = function () {
        var username = $scope.username,
        password = $scope.password;
        if (username !== undefined && username !== '' &&
            password !== undefined && password !== '') {
            loginService.login(username, password);
        } else {
            $scope.login_error = "请输入用户名密码";
        }
    };
};

loginCtrl.$inject = ['$scope', '$http', 'loginService', '$location'];

var logoutCtrl = function ($scope, $http, $location, loginService) {
    "use strict";
    loginService.logout();
};

logoutCtrl.$inject = ['$scope', '$http', '$location', 'loginService'];

angular.module('mainApp').controller('loginCtrl', loginCtrl);
angular.module('mainApp').controller('logoutCtrl', logoutCtrl);

describe("Unit: Testing Login Controllers", function () {

    var $controller, loginCtrl;

    beforeEach(module('mainApp'));

    beforeEach(inject(function (_$controller_) {
        // The injector unwraps the underscores (_) from around the parameter names when matching
        $controller = _$controller_;
    }));

    describe('Login Controller Specs', function () {
        it('Should have a login controller', function () {
            var $scope = {};
            var controller = $controller('loginCtrl', {$scope: $scope});
            expect(controller).not.toBe(undefined);
            expect(controller).not.toBe(null);
        });

        it('Should return error msg when username and password are undefined', function () {
            var $scope = {
                username: undefined,
                password: undefined
            };
            $controller('loginCtrl', {$scope: $scope});
            $scope.login();
            expect($scope.login_errors.length).toBe(1);
            expect($scope.login_errors).toContain('请输入用户名密码');
        });

        it('Should return error msg when only username is undefined', function () {
            var $scope = {
                username: undefined,
                password: "xxx"
            };
            $controller('loginCtrl', {$scope: $scope});
            $scope.login();
            expect($scope.login_errors.length).toBe(1);
            expect($scope.login_errors).toContain('请输入用户名密码');
        });

        it('Should return error msg when only password is undefined', function () {
            var $scope = {
                username: "user",
                password: undefined
            };
            $controller('loginCtrl', {$scope: $scope});
            $scope.login();
            expect($scope.login_errors.length).toBe(1);
            expect($scope.login_errors).toContain('请输入用户名密码');
        });

        it('Should return error msg when username and password are empty', function () {
            var $scope = {
                username: '',
                password: ''
            };
            $controller('loginCtrl', {$scope: $scope});
            $scope.login();
            expect($scope.login_errors.length).toBe(1);
            expect($scope.login_errors).toContain('请输入用户名密码');
        });

        it('Should return error msg when only username is empty', function () {
            var $scope = {
                username: '',
                password: "xxx"
            };
            $controller('loginCtrl', {$scope: $scope});
            $scope.login();
            expect($scope.login_errors.length).toBe(1);
            expect($scope.login_errors).toContain('请输入用户名密码');
        });

        it('Should return error msg when only password is empty', function () {
            var $scope = {
                username: "user",
                password: ''
            };
            $controller('loginCtrl', {$scope: $scope});
            $scope.login();
            expect($scope.login_errors.length).toBe(1);
            expect($scope.login_errors).toContain('请输入用户名密码');
        });

        it('Should have a logout controller', function () {
            var $scope = {};
            var controller = $controller('logoutCtrl', {$scope: $scope});
            expect(controller).not.toBe(undefined);
            expect(controller).not.toBe(null);
        });
    });

});
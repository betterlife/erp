describe("Unit: Testing Configs and Main Controller", function () {

    var $controller;

    beforeEach(module('mainApp'));

    beforeEach(inject(function (_$controller_) {
        // The injector unwraps the underscores (_) from around the parameter names when matching
        $controller = _$controller_;
    }));

    describe('Main Controller Specs', function () {
        it('Main Controller should be defined', function () {
            var $scope = {};
            var controller = $controller('mainController', {$scope: $scope});
            expect(controller).not.toBe(undefined);
            expect(controller).not.toBe(null);
        });
    });
});
describe("Unit: Testing Login Controllers", function () {

    var $filter;

    beforeEach(module('mainApp'));

    beforeEach(inject(function (_$filter_) {
        // The injector unwraps the underscores (_) from around the parameter names when matching
        $filter = _$filter_;
    }));

    describe('Remove space from string', function () {
        it('The filter nospace should exists', function () {
            expect($filter('nospace')).not.toBe(undefined);
            expect($filter('nospace')).not.toBe(null);
        });

        it("Should remove space from a normal String", function () {
            var string = 'hello world', result;
            result = $filter('nospace')(string);
            expect(result).toEqual('helloworld');
        });

        it("Should remove space from beginning and end of a String", function () {
            var string = ' world ', result;
            result = $filter('nospace')(string);
            expect(result).toEqual('world');
        });

        it("Should remove space from Chinese String", function () {
            var string = ' 我是中文 哈哈哈 ', result;
            result = $filter('nospace')(string);
            expect(result).toEqual('我是中文哈哈哈');
        });
    });

    describe('Uncapitalize English character', function () {

        it('The filter uncapitalize should exists', function () {
            expect($filter('uncapitalize')).not.toBe(undefined);
            expect($filter('uncapitalize')).not.toBe(null);
        });

        it("Change first character to lower case for English", function () {
            var result, string = 'Hello WoRLd!';
            result = $filter('uncapitalize')(string);
            expect(result).toEqual('hello woRLd!');
        });

        it("String should keep no change for Chinese", function () {
            var result, string = ' 我是中文 哈哈哈 ';
            result = $filter('uncapitalize')(string);
            expect(result).toEqual(' 我是中文 哈哈哈 ');
        });
    });
});
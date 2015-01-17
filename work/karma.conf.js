module.exports = function(config){
    config.set({

        basePath : '.',

        preprocessors: {
            // source files, that you wanna generate coverage for
            // do not include tests or libraries
            // (these files will be instrumented by Istanbul)
            'WebContent/js/app/**/*.js': ['coverage']
          },

        files : [
            'WebContent/js/bower_components/angular/angular.js',
            'WebContent/js/bower_components/angular-route/angular-route.js',
            'WebContent/js/bower_components/angular-mocks/angular-mocks.js',
            'WebContent/js/bower_components/angular-cookies/angular-cookies.js',
            'WebContent/js/bower_components/angular-bootstrap/ui-bootstrap.js',
            'WebContent/js/bower_components/angular-ui-grid/ui-grid.js',
            'WebContent/js/app/**/*.js',
            'test/js/app/**/*.js'
        ],

        //autoWatch : true,
        singleRun: true,
        frameworks: ['jasmine'],
        browsers : ['Chrome'],
        reporters : ['progress', 'junit',  'coverage'],
        plugins : [
            'karma-chrome-launcher',
            'karma-jasmine',
            'karma-junit-reporter',
            'karma-coverage'
        ],

        junitReporter : {
            outputFile: '../target/data/test/javascript/js.xml'
        },

        coverageReporter: {
            reporters : [
                {
                    type   : 'lcovonly',
                    dir    : '../target/data/coverage/javascript',
                    subdir : '.',
                    file   : 'lcov.info'
                }, {
                    type   : 'html',
                    dir    : '../target/reports/coverage/javascript',
                    subdir : '.'
                }
            ]
        }

    });
};

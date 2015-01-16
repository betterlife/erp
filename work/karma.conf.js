module.exports = function(config){
    config.set({

        basePath : '.',

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

        autoWatch : true,
        singleRun: true,
        frameworks: ['jasmine'],
        browsers : ['Chrome'],

        plugins : [
            'karma-chrome-launcher',
            'karma-jasmine',
            'karma-junit-reporter'
        ],

        junitReporter : {
            outputFile: '../target/data/junit/js.xml',
            suite: 'unit'
        }

    });
};

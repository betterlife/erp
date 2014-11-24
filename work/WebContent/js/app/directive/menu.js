/**
 * Created by larry on 11/24/14.
 */
 var menuItemDirective = function () {
    console.log("Menu Item");
    return {
        restrict: 'AE',
        templateUrl: '/templates/menuItem.html',
        replace: true,
        scope: {
            entitylabel : '@entitylabel'
        }
    }
};

angular.module('mainApp').directive("menuitem", menuItemDirective);

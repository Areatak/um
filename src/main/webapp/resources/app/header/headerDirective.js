angular.module('myApp')
    .directive('header', function () {
        return {
            restrict:'A',
            replace: true,
            templateUrl: '/resources/app/header/header.html',
            controller: 'HeaderController',
            controllerAs: 'ctrl'
        }
    });
angular.module('myApp')
    .directive('stickyHeader', function () {
        return {
            restrict:'A',
            replace: true,
            templateUrl: '/resources/app/header/stickyHeader.html',
            controller: 'HeaderController',
            controllerAs: 'ctrl'
        }
    });
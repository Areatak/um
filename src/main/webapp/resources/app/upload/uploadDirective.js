angular.module('myApp')
    .directive('upload', function () {
        return {
            restrict:'A',
            replace: true,
            templateUrl: '/resources/app/upload/upload.html',
            controller: 'UploadController'
        }
    });
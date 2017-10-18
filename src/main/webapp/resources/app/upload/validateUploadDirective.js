angular.module('myApp')
    .directive('validateUpload', function () {
        return {
            restrict:'A',
            replace: true,
            templateUrl: '/resources/app/upload/validateUpload.html',
            controller: 'ValidateUploadController'
        }
    });
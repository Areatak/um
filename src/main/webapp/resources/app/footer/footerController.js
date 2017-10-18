angular.module('myApp')
    .controller('FooterController', ['$scope', '$mdToast', 'BrowserFactory', function ($scope, $mdToast, BrowserFactory) {
        var browser = BrowserFactory.version()
        /*if (browser === 'unknown') {
            $mdToast.show({
                hideDelay: 3000,
                position: 'top center',
                controller: 'FooterController',
                templateUrl: '/resources/app/footer/unsupportedBrowser.html'
            });
        }*/
        $scope.closeToast = function () {
            $mdToast.hide()
        }
        // var browser = BrowserFactory.version()
        // if (browser === 'ie') {
        //     alert("IE")
        // }
    }]);
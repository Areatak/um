angular.module('myApp')
    .controller('ErrorToastController', ['$scope', 'msg', '$mdToast', '$location', 'UtilsFactory', 'StorageFactory', 'UserFactory',
        function ($scope, msg, $mdToast, $location, UtilsFactory, StorageFactory, UserFactory) {
            $scope.msg = msg;
            $scope.closeToast = function() {
                if (isDlgOpen) return;

                $mdToast
                    .hide()
                    .then(function() {
                        isDlgOpen = false;
                    });
            };
            $scope.goToTnxDetail = function () {
                $location.path('/transaction/detail/' + $scope.msg.txId)
            }
        }]);
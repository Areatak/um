angular.module('myApp')
    .controller('ForgetPasswordController', ['$scope', '$mdDialog', 'UtilsFactory',
        function ($scope, $mdDialog, UtilsFactory) {
            $scope.msg = {}
            $scope.loading = false
            $scope.userData = {
                email: ''
            };
            $scope.submit = function () {
                $scope.msg = {}
                $scope.loading = true
                UtilsFactory.HttpPost('/forgotPassword', {
                    email: $scope.userData.email
                })
                    .then(function (payload) {
                        $scope.loading = false;
                        if (payload.status === 200) {
                            $scope.userData = {};
                            $mdDialog.hide('cancel');
                        } else {
                            $scope.msg.status = payload.status
                            $scope.msg.type = 'error'
                        }
                    })
                    .catch(function (err) {
                        $scope.loading = false;
                        $scope.msg.status = err.status;
                        $scope.msg.type = 'error'
                    })
            };
            $scope.cancel = function () {
                $mdDialog.hide('cancel');
            };
        }]);
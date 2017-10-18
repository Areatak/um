angular.module('myApp')
    .controller('SigninController', ['$scope', '$mdDialog', '$mdToast', 'UtilsFactory', 'StorageFactory', 'UserFactory', '$translate',
        function ($scope, $mdDialog, $mdToast, UtilsFactory, StorageFactory, UserFactory, $translate) {
            $scope.msg = {}
            $scope.loading = false

            $scope.userData = {
                email: '',
                password: ''
            };
            $scope.submit = function () {
                $scope.msg = {}
                $scope.loading = true
                var passHash = UtilsFactory.SHA256($scope.userData.password)

                UserFactory.signIn({
                    email: $scope.userData.email,
                    passwordHash: passHash,
                    platform: 'web'
                })
                    .then(function (payload) {
                        $scope.loading = false
                        if (payload.status === 200) {
                            $scope.userData = {}
                            $scope.msg.type = 'ok'
                            $mdDialog.hide('cancel');
                        } else {
                            $scope.msg.status = payload.status
                            $scope.msg.type = 'error'
                        }
                        UtilsFactory.Toast({
                            text: $translate.instant('signin.' + payload.status),
                            type: $scope.msg.type
                        })
                    })
                    .catch(function (err) {
                        $scope.loading = false
                        $scope.msg.status = err.status
                        $scope.msg.type = 'error'
                        UtilsFactory.Toast({
                            text: $translate.instant('signin.' + err.status),
                            type: 'error'
                        })
                    })

            };
            $scope.cancel = function () {
                $mdDialog.hide('cancel');
            };
            $scope.signup = function () {
                $mdDialog.show({
                    controller: 'SignupController',
                    templateUrl: '/resources/app/signup/signup.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose: true,
                })
            };
            $scope.forgetPassword = function () {
                $mdDialog.show({
                    controller: 'ForgetPasswordController',
                    templateUrl: '/resources/app/forgetPassword/forgetPassword.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose: true,
                })
            };
        }]);
angular.module('myApp')
    .controller('AuthController', ['$scope', '$mdDialog', 'UtilsFactory', 'StorageFactory', '$translate',
        function ($scope, $mdDialog, UtilsFactory, StorageFactory) {
            $scope.signupDone = false
            $scope.msg = {}
            $scope.loading = false
            $scope.userData = {
                email: '',
                password: '',
                confirmPassword: ''
            };

            $scope.signup = function () {
                $scope.msg = {}
                if ($scope.userData.password !== $scope.userData.confirmPassword) {
                    $scope.msg.text = 'Passwords does not match.'
                    $scope.msg.type = 'error'
                    return
                }
                $scope.loading = true
                var passHash = UtilsFactory.SHA256($scope.userData.password)
                var confirmPassHash = UtilsFactory.SHA256($scope.userData.confirmPassword)
                UtilsFactory.HttpPost('/signup', {
                    email: $scope.userData.email,
                    passwordHash: passHash,
                    confirmPasswordHash: confirmPassHash,
                    platform: 'WEB'
                })
                    .then(function (payload) {
                        $scope.loading = false
                        if (payload.status === 200) {
                            $scope.userData = {}
                            $mdDialog.hide('cancel');
                            $scope.signupSuccess()
                            // done
                        } else if (payload.status === 226) {
                            $scope.msg.text = 'This email is in use. Please enter another email.'
                            $scope.msg.type = 'error'
                            // email in use
                        } else if (payload.status === 400) {
                            $scope.msg.text = 'Bad data input. Check and submit again.'
                            $scope.msg.type = 'error'
                            // 400 bad request
                        }
                        return $translate('TITLE')
                    })
                    .then(function (payload) {
                        $scope.msg.text = payload
                    })
                    .catch(function (err) {
                        $scope.loading = false
                        $scope.msg.text = 'Something is not working properly. Please try again later.'
                    })
            };

            $scope.signin = function () {
                $scope.msg = {}
                $scope.loading = true
                var passHash = UtilsFactory.SHA256($scope.userData.password)
                UtilsFactory.HttpPost('/login', {
                    email: $scope.userData.email,
                    passwordHash: passHash,
                    platform: 'WEB'
                })
                    .then(function (payload) {
                        $scope.loading = false
                        if (payload.status === 200) {
                            $scope.userData = {}
                            StorageFactory.set('userId', payload.data.userId)
                            StorageFactory.set('token', payload.data.token)
                            StorageFactory.set('isLogin', true)
                            StorageFactory.set('passHash', passHash)
                            StorageFactory.set('fname', payload.data.name)
                            StorageFactory.set('lname', payload.data.lastName)
                            StorageFactory.set('pic', payload.data.profilePicAddress)
                            $mdDialog.hide('cancel');
                        }
                        return
                        // return $translate('TITLE')
                    })
                    .then(function (payload) {
                        console.log(payload)
                    })
                    .catch(function (err) {
                        $scope.loading = false
                        if (err.status === 204 || err.status === 401) {
                            $scope.msg.text = 'Incorrect username or password. Please review your credentials.'
                            $scope.msg.type = 'error'
                            // user not found
                        } else if (err.status === 400) {
                            $scope.msg.text = 'Bad data input. Check and submit again.'
                            $scope.msg.type = 'error'
                            // 400 bad request
                        }
                    })
            };


            $scope.cancel = function () {
                $mdDialog.hide('cancel');
            };
            $scope.signupSuccess = function () {
                $mdDialog.show(
                    $mdDialog.alert()
                        .parent(angular.element(document.body))
                        .clickOutsideToClose(true)
                        .title('Signup Successful')
                        .textContent('You can sign in into your account.')
                        .ariaLabel('Signup Success')
                        .ok('Ok!')
                );
            };

        }]);
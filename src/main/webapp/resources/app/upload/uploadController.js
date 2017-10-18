angular.module('myApp')
    .controller('UploadController', ['$scope', '$rootScope', '$timeout', 'Upload', 'growl', 'uploadService', '$mdDialog', 'UtilsFactory', 'StorageFactory',
        function ($scope, $rootScope, $timeout, Upload, growl, uploadService, $mdDialog, UtilsFactory, StorageFactory) {
            $scope.loading = false;
            $scope.isLogin = StorageFactory.get('isLogin')
            $rootScope.$on('LocalStorageModule.notification.setitem', function (event, data) {
                if (data.key === 'isLogin') {
                    $scope.isLogin = StorageFactory.get('isLogin')
                    if ($scope.isLogin && $scope.file) {
                        $scope.upload($scope.file)
                    }
                }
            });

            $scope.percentage = 0;
            $scope.uploadFiles = function ($file, $invalidFiles) {
            };

            $scope.checkLogin = function (file) {
                $scope.file = file;
                if (!$scope.isLogin) {
                    $mdDialog.show({
                        controller: 'SigninController',
                        templateUrl: '/resources/app/signin/signin.html',
                        parent: angular.element(document.body),
                        clickOutsideToClose: true
                    })
                }
            }
            $scope.upload = function (file) {
                if (file && $scope.isLogin) {
                    if (!file.$error) {
                        $scope.loading = true;
                        uploadService.digestFile(file)
                            .then(function (payload) {
                                $scope.success({hash: payload, file: file})
                            });
                    }
                }
            };

            $scope.success = function (data) {
                $scope.loading = false;
                $mdDialog.show({
                    locals: {
                        data: data
                    },
                    controller: 'TransactionCreateController',
                    templateUrl: '/resources/app/transaction/transactionCreate.html?v=2',
                    parent: angular.element(document.body),
                    clickOutsideToClose: true
                })
            }

            $scope.$on('createTransactionLoading', function (event, data) {
                $scope.loading = data
            });

        }]);
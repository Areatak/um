angular.module('myApp')
    .controller('RecoverController',
        ['$scope', '$filter', 'UtilsFactory', 'StorageFactory','$translate','$location','$routeParams',
            function ($scope, $filter, UtilsFactory, StorageFactory,$translate,$location,$routeParams) {
                $scope.recoverEmail = $routeParams.email;
                $scope.recoverHash = $routeParams.hash;
                $scope.recoverConfirmPassword = '';
                $scope.recoverPassword = '';


                this.submit = function(){
                    var passHash = UtilsFactory.SHA256($scope.recoverPassword);

                    UtilsFactory.HttpPost('/changePass', {
                        h: $scope.recoverHash,
                        passwordHash: passHash
                    })
                        .then(function (payload) {
                            $scope.loading = false;
                            if (payload.status === 200) {
                                $location.path('/');
                            }

                        })
                        .catch(function (err) {
                            UtilsFactory.Toast({
                                text: $translate.instant('recover.changeException'),
                                type: 'error'
                            });
                            $scope.loading = false;
                        })

                }
            }]);
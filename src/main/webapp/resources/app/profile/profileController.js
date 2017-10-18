angular.module('myApp')
    .controller('ProfileController', ['$scope', '$rootScope', '$mdDialog', 'UtilsFactory', 'StorageFactory', 'Upload', '$translate',
        function ($scope, $rootScope, $mdDialog, UtilsFactory, StorageFactory, Upload, $translate) {
            $scope.msg = {}
            $scope.loading = false
            $scope.userData = {
                fname: StorageFactory.get('fname'),
                lname: StorageFactory.get('lname'),
                password: '',
                oldPassword: '',
                changePassword: false,
                pic: ''
            };
            $scope.$on('uploadSuccess', function (file) {
                $scope.userData.pic = file;
            });
            $scope.uploadBtn = function () {
                var input = document.getElementById('fileInput');
                input.click()
            }
            $scope.upload = function () {
                var pic = $scope.pic
                Upload.upload({
                    url: 'upload/url',
                    data: {file: pic, 'username': $scope.username}
                }).then(function (resp) {
                    console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
                }, function (resp) {
                    console.log('Error status: ' + resp.status);
                }, function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                });
            };
            $scope.submit = function () {
                $scope.msg = {}
                $scope.loading = true
                var passHash
                var oldPassHash
                if ($scope.userData.changePassword && $scope.userData.password === $scope.userData.confirmPassword) {
                    passHash = UtilsFactory.SHA256($scope.userData.password)
                    oldPassHash = UtilsFactory.SHA256($scope.userData.oldPassword)
                } else if ($scope.userData.changePassword && $scope.userData.password !== $scope.userData.confirmPassword) {
                    $scope.msg.status = 412
                    $scope.msg.type = 'error'
                    UtilsFactory.Toast({
                        text: $translate.instant('profile.' + $scope.msg.status),
                        type: 'error'
                    })
                    return
                } else {
                    passHash = StorageFactory.get('passHash')
                    oldPassHash = ''
                }
                // FIXME if user does not select file, backend throws exception. And current file is not accessible in client-side.
                var pic
                if ($scope.pic) {
                    pic = $scope.pic.name
                } else {
                    pic = StorageFactory.get('pic')
                }
                var params = {
                    token: StorageFactory.get('token'),
                    passwordHash: passHash,
                    oldPasswordHash: oldPassHash,
                    name: $scope.userData.fname,
                    lastName: $scope.userData.lname,
                    profilePic: $scope.pic
                }
                UtilsFactory.HttpPostWithFile('/updateProfile', params)
                    .then(function (payload) {
                        $scope.loading = false
                        if (payload.status === 200) {
                            $scope.msg.type = 'ok'
                            StorageFactory.set('passHash', passHash)
                            StorageFactory.set('fname', $scope.userData.fname)
                            StorageFactory.set('lname', $scope.userData.lname)
                            StorageFactory.set('pic', $scope.pic)
                            $mdDialog.hide('cancel');
                            $rootScope.$broadcast('profileUpdated', true);
                            // done
                        }
                        UtilsFactory.Toast({
                            text: $translate.instant('profile.' + payload.status),
                            type: $scope.msg.type
                        })
                        return
                    })
                    .catch(function (err) {
                        $scope.loading = false
                        UtilsFactory.Toast({
                            text: $translate.instant('profile.' + err.status),
                            type: 'error'
                        })
                    })
            };
            $scope.cancel = function () {
                $mdDialog.hide('cancel');
            };
            $scope.success = function () {
                /*$mdDialog.show(
                    $mdDialog.alert()
                        .parent(angular.element(document.body))
                        .clickOutsideToClose(true)
                        .title('Profile Update Successful')
                        .ariaLabel('Profile Update Success')
                        .ok('Ok!')
                );*/
            };
        }]);

myApp
    .directive('chooseFile', function () {
        return {
            link: function ($scope, elem, attrs) {
                var button = elem.find('button');
                var input = angular.element(elem[0].querySelector('input#fileInput'));
                button.bind('click', function () {
                    input[0].click();
                });
                input.bind('change', function (e) {
                    $scope.$apply(function () {
                        var files = e.target.files;
                        if (files[0]) {
                            $scope.fileName = files[0].name;
                            $scope.$emit('uploadSuccess', scope.fileName);
                        } else {
                            $scope.fileName = null;
                        }
                    });
                });
            }
        };
    });
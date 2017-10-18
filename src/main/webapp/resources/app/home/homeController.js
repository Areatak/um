angular.module('myApp.home', [])
/*    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/:mode?', {templateUrl: '/resources/app/home/home.html', controller: 'HomeController'})
    }])*/
    .controller('HomeController', [
        '$scope', '$routeParams', '$window', '$rootScope', 'StorageFactory', '$translate', '$sce', 'UtilsFactory', '$location', '$mdDialog', 'EmailContactFactory', 'UserFactory',
        function ($scope, $routeParams, $window, $rootScope, StorageFactory, $translate, $sce, UtilsFactory, $location, $mdDialog, EmailContactFactory, UserFactory) {
            if (_paymentMode == 'true') {
                var responseType = 'error'
                if (_status == 100 || _status == 101) {
                    responseType = 'ok'
                }
                var data = {
                    type: responseType,
                    text: _statusString,
                    refId: _refId
                }
                $mdDialog.show({
                    locals: {
                        data: data
                    },
                    controller: 'PaymentResultController',
                    templateUrl: '/resources/app/payment/paymentResult.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose: true
                })
                if (responseType === 'ok') {
                    UserFactory.getUserCredit()
                        .then(function (payload) {
                            return StorageFactory.set('credit', payload);
                        })
                }
            }
            if (_recoverMode === true) {
                if (_recoverExpired === true) {
                    UtilsFactory.Toast({
                        text: $translate.instant('recover.expired'),
                        type: 'error'
                    })
                } else {
                    var email = _recoverMail;
                    var hash = _recoverHash;

                    if (!email || !hash) {
                        UtilsFactory.Toast({
                            text: $translate.instant('recover.invalidData'),
                            type: 'error'
                        })
                    } else {
                        _recoverMail = null;
                        _recoverHash = null;
                        _recoverExpired = false;
                        _recoverMode = false;

                        // $rootScope.recoverMail = email;
                        // $rootScope.recoverHash = hash;

                        $location.path('/recover').search({email: email, hash: hash});
                    }
                }
            }
            if (_socialAuthMode === true) {
                if (_socialAuthSuccess === false) {
                    UtilsFactory.Toast({
                        text: $translate.instant('auth.google.permissionDenied'),
                        type: 'error'
                    })
                } else {
                    var emails = _emails;
                    var photos = _photos;
                    EmailContactFactory.saveContacts(emails, photos);

                    _photos = null;
                    _emails = null;
                    _socialAuthSuccess = false;
                    _socialAuthMode = false;

                    $location.path('/invite');
                }
            }
            this.config = {

                sources: [
                    // {src: $sce.trustAsResourceUrl("http://static.videogular.com/assets/videos/videogular.mp4"), type: "video/mp4"},
                    // {src: $sce.trustAsResourceUrl("http://static.videogular.com/assets/videos/videogular.webm"), type: "video/webm"},
                    // {src: $sce.trustAsResourceUrl("http://static.videogular.com/assets/videos/videogular.ogg"), type: "video/ogg"}
                ],
                tracks: [
                    {
                        src: "http://www.videogular.com/assets/subs/pale-blue-dot.vtt",
                        kind: "subtitles",
                        srclang: "en",
                        label: "English",
                        default: ""
                    }
                ],
                theme: "/resources/bower_components/videogular-themes-default/videogular.css",
                plugins: {
                    poster: "http://www.videogular.com/assets/images/videogular.png"
                }
            };
	        $scope.isLogin = StorageFactory.get('isLogin')
	        var mode = $routeParams.mode;
	        if (mode === 'register' && !$scope.isLogin) {
		        $mdDialog.show({
			        controller: 'SignupController',
			        templateUrl: '/resources/app/signup/signup.html',
			        parent: angular.element(document.body),
			        clickOutsideToClose: true
		        })
            } else if (mode !== 'register') {
            }

            $rootScope.$on('LocalStorageModule.notification.setitem', function (event, data) {
                if (data.key === 'isLogin') $scope.isLogin = StorageFactory.get('isLogin')
            });

            this.isCollapsed1 = true;
            this.isCollapsed2 = true;
            this.isCollapsed3 = true;
            this.isCollapsed4 = true;
        }]);
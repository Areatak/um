angular.module('myApp')
	.controller('HeaderController',
		['$scope', '$rootScope', '$translate', '$mdSidenav', '$mdDialog', 'StorageFactory', 'UtilsFactory', '$location', 'EmailContactFactory',
			function ($scope, $rootScope, $translate, $mdSidenav, $mdDialog, StorageFactory, UtilsFactory, $location, EmailContactFactory) {
				$scope.currentNavItem = "home";
				$scope.lang = StorageFactory.get('lang');
				init()
				$rootScope.$on('LocalStorageModule.notification.setitem', function (event, data) {
					$translate.use(StorageFactory.get('lang'));
					if (data.key === 'isLogin') {
						$scope.isLogin = StorageFactory.get('isLogin')
						if (!$scope.isLogin) {
							$scope.fname = StorageFactory.get('fname')
							$scope.lname = StorageFactory.get('lname')
							$scope.email = StorageFactory.get('email')
							$scope.pic = undefined
						} else {
							$scope.currentNavItem = "home";
						}
					} else if (data.key === 'token') {
						$scope.token = StorageFactory.get('token')
						if ($scope.token) {
							downloadProfile()
								.then(function (payload) {
									StorageFactory.set('pic', payload)
									$scope.pic = payload
								})
						} else {
							$scope.userIdHash = undefined
							$scope.pic = undefined
						}
					} else if (data.key === 'userId') {
						$scope.userIdHash = UtilsFactory.SHA256(StorageFactory.get('userId'))
						// $scope.identicon = generateIdenticon($scope.userIdHash)
					} else if (data.key === 'fname') {
						$scope.fname = StorageFactory.get('fname')
					} else if (data.key === 'lname') {
						$scope.lname = StorageFactory.get('lname')
					} else if (data.key === 'email') {
						$scope.email = StorageFactory.get('email')
					} else if (data.key === 'credit') {
						$scope.credit = StorageFactory.get('credit')
					}
				});
				$scope.$on('profileUpdated', function (event, arg) {
					downloadProfile()
						.then(function (payload) {
							StorageFactory.set('pic', payload)
							$scope.pic = payload
						})
						.catch(function (err) {
							console.error(err)
						})
				});
				$scope.$on('$routeChangeSuccess', function (next, current) {
					$scope.closeSideNav();
				});
				function init() {
					$scope.isLogin = StorageFactory.get('isLogin')
					$scope.token = StorageFactory.get('token')
					$scope.pic = StorageFactory.get('pic')
					$scope.fname = StorageFactory.get('fname')
					$scope.lname = StorageFactory.get('lname')
					$scope.email = StorageFactory.get('email')
					$scope.credit = StorageFactory.get('credit')
					if ($scope.isLogin) {
						$scope.userIdHash = UtilsFactory.SHA256(StorageFactory.get('userId'))
						// $scope.identicon = generateIdenticon($scope.userIdHash)
						downloadProfile()
							.then(function (payload) {
								StorageFactory.set('pic', payload)
								$scope.pic = payload
							})
							.catch(function (err) {
								console.error(err)
							})
					}
				}

				function downloadProfile() {
					return UtilsFactory.HttpGet('/downloadProfile/' + $scope.token, {})
						.then(function (payload) {
							var content
							if (payload.data) {
								content = _arrayBufferToBase64(payload.data)
							}
							return content
						})
						.catch(function (err) {
							console.error(err)
						})
				}

				function _arrayBufferToBase64(buffer) {
					var binary = '';
					var bytes = new Uint8Array(buffer);
					var len = bytes.byteLength;
					for (var i = 0; i < len; i++) {
						binary += String.fromCharCode(bytes[i]);
					}
					return window.btoa(binary);
				}

				this.signup = function (ev) {
					$mdDialog.show({
						controller: 'SignupController',
						templateUrl: '/resources/app/signup/signup.html',
						parent: angular.element(document.body),
						targetEvent: ev,
						clickOutsideToClose: true,
					})
				};
				this.signin = function (ev) {
					$mdDialog.show({
						controller: 'SigninController',
						templateUrl: '/resources/app/signin/signin.html',
						parent: angular.element(document.body),
						targetEvent: ev,
						clickOutsideToClose: true,
					})
				};
				this.signout = function () {
					StorageFactory.clear();
					StorageFactory.set('isLogin', false);
					$location.path('/');
				};
				this.profile = function (ev) {
					$mdDialog.show({
						controller: 'ProfileController',
						templateUrl: '/resources/app/profile/profile.html',
						parent: angular.element(document.body),
						targetEvent: ev,
						clickOutsideToClose: true,
					})
					setTimeout(function () {
						$scope.closeSideNav();
					}, 100)
				};
				this.payment = function (ev) {
					$mdDialog.show({
						controller: 'PaymentController',
						templateUrl: '/resources/app/payment/payment.html',
						parent: angular.element(document.body),
						targetEvent: ev,
						clickOutsideToClose: true,
					})
					setTimeout(function () {
						$scope.closeSideNav();
					}, 100)
				};
				this.transactionList = function () {
					$location.path('/transaction/list');
				};

				$scope.isOpenRight = function () {
					return $mdSidenav('right').isOpen();
				};

				$scope.toggleSideNav = function () {
					$mdSidenav('right')
						.toggle()
						.then(function () {
						});
				}

				$scope.isSideNavOpen = function () {
					return $mdSidenav('right').isOpen();
				};

				$scope.closeSideNav = function () {
					$mdSidenav('right').close()
				}

				var originatorEv;
				this.openMenu = function ($mdMenu, ev) {
					originatorEv = ev;
					$mdMenu.open(ev);
				};

				this.closeMenu = function ($mdMenu, ev) {
					originatorEv = ev;
					$mdMenu.close(ev);
				};

				this.invite = function () {
					EmailContactFactory.fetchContacts();
				};

				this.changeLang = function (lang) {
					$translate.use(lang);
					StorageFactory.set('lang', lang);
				};

				$scope.langChange = function () {
					$translate.use($scope.lang);
					StorageFactory.set('lang', $scope.lang);
				};
			}]);

myApp
	.directive("jdenticonHash", function () {
		return {
			restrict: "A",
			link: function (scope, el, attrs) {
				// el is jqLite wrapped, needs to be unwrapped before passed to Jdenticon
				jdenticon.update(el[0], attrs.jdenticonHash);
			}
		};
	})

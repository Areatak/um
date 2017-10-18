angular.module('myApp')
	.controller('SignupController', ['$scope', '$mdDialog', 'UtilsFactory', 'UserFactory', '$translate', '$http',
		function ($scope, $mdDialog, UtilsFactory, UserFactory, $translate, $http) {
			$http.get('/resources/res/country.json')
				.then(function (response) {
					$scope.countries = response.data
				})
				.catch(function (err) {
					console.log(err)
				})
			$scope.msg = {}
			$scope.loading = false
			$scope.userData = {
				email: '',
				password: '',
				confirmPassword: ''
			};

			$scope.submit = function () {
				$scope.loading = true
				$scope.msg = {}
				if ($scope.userData.password !== $scope.userData.confirmPassword) {
					$scope.msg.status = 412
					$scope.msg.type = 'error'
					UtilsFactory.Toast({
						text: $translate.instant('signup.' + $scope.msg.status),
						type: 'error'
					})
					return
				}
				var passHash = UtilsFactory.SHA256($scope.userData.password)
				var confirmPassHash = UtilsFactory.SHA256($scope.userData.confirmPassword)
				var params = _.clone($scope.userData)
				params.passwordHash = passHash
				params.confirmPasswordHash = confirmPassHash
				params.platform = 'WEB'
				delete params.password
				delete params.confirmPassword
				if ($scope.phoneCountry) {
					params.phone = $scope.phoneCountry + params.phone
				}
				var companyCountryDiallingCode = ''
				if ($scope.userData.companyCountry) {
					companyCountryDiallingCode = _.find($scope.countries, {code: $scope.userData.companyCountry}).diallingCode;
				}
				params.companyPhone = companyCountryDiallingCode + params.companyPhone

				UtilsFactory.HttpPost('/signup', params /*{
				 email: $scope.userData.email,
				 passwordHash: passHash,
				 confirmPasswordHash: confirmPassHash,
				 platform: 'WEB'
				 }*/)
					.then(function (payload) {
						$scope.loading = false
						$scope.msg.status = payload.status
						if (payload.status === 200) {
							$scope.msg.type = 'ok'
							$mdDialog.hide('cancel');
							$scope.success()
						} else {
							$scope.msg.status = payload.status
							$scope.msg.type = 'error'
							UtilsFactory.Toast({
								text: $translate.instant('signup.' + payload.status),
								type: 'error'
							})
						}
					})
					.catch(function (err) {
						$scope.loading = false
						$scope.msg.status = err.status
						$scope.msg.type = 'error'
						UtilsFactory.Toast({
							text: $translate.instant('signup.' + err.status),
							type: 'error'
						})
					})
			};
			$scope.cancel = function () {
				$mdDialog.hide('cancel');
			};
			$scope.success = function () {
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
						} else {
							$scope.msg.status = payload.status
							$scope.msg.type = 'error'
						}
						UtilsFactory.Toast({
							text: $translate.instant('signup.' + payload.status),
							type: $scope.msg.type
						})
					})
					.catch(function (err) {
						$scope.loading = false
						$scope.msg.status = err.status
						$scope.msg.type = 'error'
						UtilsFactory.Toast({
							text: $translate.instant('signup.' + err.status),
							type: $scope.msg.type
						})
					})
			};
			$scope.signin = function () {
				$mdDialog.show({
					controller: 'SigninController',
					templateUrl: '/resources/app/signin/signin.html',
					parent: angular.element(document.body),
					clickOutsideToClose: true,
				})
			}
		}]);
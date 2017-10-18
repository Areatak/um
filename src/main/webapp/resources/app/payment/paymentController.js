angular.module('myApp')
	.controller('PaymentController', ['$scope', '$location', '$mdDialog', '$mdToast', 'UtilsFactory', 'StorageFactory', 'UserFactory', '$translate',
		function ($scope, $location, $mdDialog, $mdToast, UtilsFactory, StorageFactory, UserFactory, $translate) {
			$scope.msg = {}
			$scope.loading = false
			$scope.paymentData = {
				ubit: 20,
				rials: 0,
				exchangeRate: 0
			};
			init()
			function init() {
				UtilsFactory.HttpPost('/getExchangeRate', {})
					.then(function (payload) {
						$scope.paymentData.exchangeRate = payload.data
						$scope.paymentData.rials = $scope.paymentData.exchangeRate * $scope.paymentData.ubit * 10
					})
					.catch(function (err) {
						$scope.paymentData.exchangeRate = 0
					})
				$scope.$watch('paymentData.ubit', function () {
					$scope.paymentData.rials = $scope.paymentData.exchangeRate * $scope.paymentData.ubit * 10
				})
			}

			$scope.submit = function () {
				$scope.msg = {}
				$scope.loading = true
				var token = StorageFactory.get('token')
				paymentRequest('result', token, 'Utabit', $scope.paymentData.ubit)
				/*var paymentUrl = $location.protocol() + '://' + $location.host() + ':' + $location.port() + '/buyCredit?token=' + token + '&' + 'ubit=' + $scope.paymentData.ubit
				 window.open(paymentUrl)*/

			};
			$scope.cancel = function () {
				$mdDialog.hide('cancel');
			};

			function paymentRequest(divId, token, coinType, ubit) {

				function guid() {
					function s4() {
						return Math.floor((1 + Math.random()) * 0x10000)
							.toString(16)
							.substring(1);
					}

					return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
						s4() + '-' + s4() + s4() + s4();
				}

				var xhttp = new XMLHttpRequest();
				var seq = '';
				xhttp.onreadystatechange = function () {
					if (this.readyState === 4 && this.status === 200) {
						// document.getElementById(divId).innerHTML = this.responseText;
						// seq = JSON.parse(this.responseText)["seq"];
						$mdDialog.show({
							locals: {
								data: {
									response: this.responseText,
									uuid: uuid
								}
							},
							controller: 'PaymentResultController',
							templateUrl: '/resources/app/payment/paymentResult.html',
							parent: angular.element(document.body)
						})
					}
				};
				var uuid = guid();
				xhttp.open("POST", "/buyCreditWithCoin", true);
				xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				xhttp.send('token=' + token + '&coinType=' + coinType + '&amount=' + ubit + '&stomp=' + uuid);
			}

		}]);
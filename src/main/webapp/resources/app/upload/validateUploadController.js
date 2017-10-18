angular.module('myApp')
	.controller('ValidateUploadController', ['$scope', '$timeout', 'Upload', 'growl', 'uploadService', '$mdDialog', 'UtilsFactory', '$translate', 'BrowserFactory',
		function ($scope, $timeout, Upload, growl, uploadService, $mdDialog, UtilsFactory, $translate, BrowserFactory) {
			$scope.status = 'ready';
			$scope.loading = false;
			$scope.percentage = 0;
			$scope.validateUpload = function (file) {
				$scope.status = 'uploading';
				if (file) {
					if (!file.$error) {
						$scope.loading = true;
						// var browser = BrowserFactory.version()
						uploadService.digestFile(file)
							.then(function (payload) {
								$scope.validate({hash: payload})
							});
					}
				}
			};
			$scope.validate = function (data) {
				UtilsFactory.HttpPost('/validateTnxBydataHash', {
					dataHash: data.hash
				})
					.then(function (payload) {
						$scope.status = 'ok'
						$timeout(function () {
							$scope.status = 'ready'
						}, 3000)
						$scope.loading = false;
						if (payload.data) {
							// data.isTrxMine = false
							// data.txId = payload.data.txId
							payload.data.isTrxMine = false
							payload.data.dataComplete = true
							$mdDialog.show({
								locals: {
									data: payload.data,
								},
								controller: 'TransactionSummaryController',
								templateUrl: '/resources/app/transaction/transactionSummary.html?v=2',
								parent: angular.element(document.body),
								clickOutsideToClose: true
							})
						} else {
							$scope.status = 'error';
							$mdDialog.show({
								controller: 'ValidateUploadController',
								templateUrl: '/resources/app/transaction/invalidAlert.html',
								parent: angular.element(document.body),
								clickOutsideToClose: true
							})
							/*$mdDialog.show(
							 $mdDialog.alert()
							 .parent(angular.element(document.body))
							 .clickOutsideToClose(true)
							 .title('Not Found!')
							 .textContent("This file is not registered yet.")
							 // .textContent($translate.instant("transaction.validate.404"))
							 .ariaLabel('DocumentValidateError')
							 .ok('Close')
							 );*/
						}
					})
					.catch(function (err) {
						$scope.loading = false;
						$scope.status = 'error';
						$timeout(function () {
							$scope.status = 'ready'
						}, 3000);
						$mdDialog.show({
							templateUrl: '/resources/app/transaction/invalidAlert.html',
							parent: angular.element(document.body),
							clickOutsideToClose: true
						})
					})
			}
			$scope.cancel = function () {
				$scope.status = 'ready';
				$scope.loading = false;
				$mdDialog.hide('cancel');
			};
		}]);
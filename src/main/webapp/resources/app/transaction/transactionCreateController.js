angular.module('myApp')
	.controller('TransactionCreateController', ['$scope', '$rootScope', 'data', '$mdToast', '$mdDialog', 'UtilsFactory', 'StorageFactory', '$translate',
		function ($scope, $rootScope, data, $mdToast, $mdDialog, UtilsFactory, StorageFactory, $translate) {
			init(data);

			function buyCredit() {
				//TODO: fanroosh 1396/02/23 - redirect to Bank payment
			}

			function init(data) {
				$scope.trx = {
					hash: data.hash,
					fee: null,
					payByCredit: true,
					upload: false,
					desc: ''
				};
				$scope.user = {
					credit: 0
				};
				$scope.data = data;
				Promise.all([
					getTransactionFee(),
					getUserCredit()
				])
					.then(function (payload) {
						$scope.trx.fee = payload[0]
						$scope.user.credit = payload[1].credit
					})
					.catch(function (err) {
						console.error('An error occurred!')
					})
				$scope.docType = null;
			}

			function getTransactionFee() {
				return UtilsFactory.HttpPost('/getTxFee', {})
					.then(function (payload) {
						return payload.data
					})
					.catch(function (err) {
						return new Error(500)
					})
			}

			function getUserCredit() {
				return UtilsFactory.HttpPost('/getUser', {
					token: StorageFactory.get('token')
				})
					.then(function (payload) {
						return payload.data
					})
					.catch(function (err) {
						return new Error(500)
					})
			}

			$scope.submit = function () {
				if ($scope.docType === null) {
					UtilsFactory.Toast({
						text: $translate.instant('transaction.create.1001'),
						type: 'error'
					})
					return;
				}
				$scope.msg = {}
				$rootScope.$broadcast('createTransactionLoading', true);
				$mdDialog.hide('cancel');
				var params = {
					token: StorageFactory.get('token'),
					hash: data.hash,
					desc: $scope.trx.desc,
					type: $scope.docType
				}
				if ($scope.trx.upload) {
					params.uploadedFile = data.file
				}
				UtilsFactory.HttpPostWithFile('/createTnx', params)
					.then(function (payload) {
						$rootScope.$broadcast('createTransactionLoading', false);
						$scope.msg.status = payload.status
						if (payload.status === 200) {
							$scope.msg.type = 'ok'
							$scope.success(payload.data)
						} else if (payload.status === 208) {
							// $scope.txId = payload.data.txId
							$mdToast.show({
								hideDelay: 60000,
								position: 'top right',
								controller: 'ErrorToastController',
								templateUrl: '/resources/app/transaction/duplicatedDoc.html',
								locals: {
									msg: {
										txId: payload.data.txId
									}
								}
							});
						} else {
							$scope.msg.type = 'error'
							UtilsFactory.Toast({
								text: $translate.instant('transaction.create.' + payload.status),
								type: $scope.msg.type
							})
						}
					})
					.catch(function (err) {
						$rootScope.$broadcast('createTransactionLoading', false);
						$scope.msg.status = err.status
						$scope.msg.type = 'error'
						if (err.status === 208) {
							$mdToast.show({
								hideDelay: 3000,
								position: 'top right',
								// controller: 'TransactionCreateController',
								templateUrl: '/resources/app/transaction/duplicatedDoc.html',
							});
						} else {
							UtilsFactory.Toast({
								text: $translate.instant('transaction.create.' + err.status),
								type: $scope.msg.type
							})
						}

					})
			};
			$scope.cancel = function () {
				$mdDialog.hide('cancel');
			};
			$scope.success = function (trx) {
				data.id = trx.id;
				// data.isTrxMine = true;
				// data.txId = trx.txId;
				$mdDialog.show({
					locals: {
						data: data
					},
					controller: 'TransactionSummaryController',
					templateUrl: '/resources/app/transaction/transactionSummary.html?v=2',
					parent: angular.element(document.body),
					clickOutsideToClose: true
				})
			};

			$scope.selectType = function (type) {
				$scope.docType = type;
			}
		}]);

/*
 angular.module('myApp')
 .controller('TransactionToastController', ['$scope', '$rootScope', 'data', '$mdToast', '$mdDialog', 'UtilsFactory', 'StorageFactory', '$translate',
 function ($scope, $rootScope, data, $mdToast, $mdDialog, UtilsFactory, StorageFactory, $translate) {
 }])*/

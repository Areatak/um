myApp
	.controller('TransactionDetailController', ['$scope', '$routeParams', 'UtilsFactory', 'StorageFactory', '_', '$translate',
		function ($scope, $routeParams, UtilsFactory, StorageFactory, _, $translate) {
			setTimeout(function () {
				init()
			})
			function init() {
				$scope.trx = {}
				$scope.token = StorageFactory.get('token')
				$scope.userId = StorageFactory.get('userId')
				var url
				var params = {txId: $routeParams.id}
				// if is mine transaction
				if ($scope.token) {
					url = 'findUserTnxByTxId'
					params.token = $scope.token
				} else {
					url = 'findTnxByTxId'
				}
				$scope.loading = true;
				return UtilsFactory.HttpPost(url, params)
					.then(function (payload) {
						$scope.loading = false;
						if (!payload.data) {
							$scope.trx = undefined
							return
						}
						var now = moment(new Date());
						var created = moment(new Date(payload.data.txCreatedDate));
						var diff = moment.duration(now.diff(created));
						var totalMinutesAgo = parseInt(diff.asMinutes());
						$scope.trx = {
							hash: payload.data.dataHash,
							created: moment.unix(payload.data.txCreatedDate / 1000).format($translate.instant('dateformat')),
							fileId: payload.data.fileId,
							confirmations: payload.data.confirmations,
							txId: payload.data.txId,
							desc: payload.data.desc,
							paymentStatus: payload.data.paymentStatus,
							paymentDate: moment.unix(payload.data.paymentDate / 1000).format($translate.instant('dateformat')),
							duration: {
								days: parseInt(diff.days()),
								hours: parseInt(diff.hours()),
								minutes: parseInt(diff.minutes()),
								seconds: parseInt(diff.seconds())
							},
							minutesAgo: totalMinutesAgo,
							minutesToConfirmFrom: 10 - totalMinutesAgo,
							minutesToConfirmTo: 20 - totalMinutesAgo,
							downloadable: (payload.data.userId == $scope.userId ? true : false)
						}
						return
					})
					.catch(function (err) {
						$scope.loading = false;
						$scope.trx = undefined
					})
			}

			$scope.downloadFile = function (fileId) {
				var token = StorageFactory.get('token')
				return UtilsFactory.HttpGet('/downloadTnxFile/' + token + '&' + fileId, {})
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
		}]);
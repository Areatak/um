myApp
    .controller('TransactionListController', ['$scope', 'UtilsFactory', 'StorageFactory', '_', '$translate',
        function ($scope, UtilsFactory, StorageFactory, _, $translate) {
            $scope.getTransactions = function () {
                return UtilsFactory.HttpPost('/listUserTransactions', {
                    token: StorageFactory.get('token')
                })
                    .then(function (payload) {
                        $scope.transactions = []
                        var transaction = {}
                        _.forEach(payload.data, function (txData, txId) {
                            transaction = {
                                txId: txId,
                                created: moment.unix(txData.txCreatedDate / 1000).format($translate.instant('dateformat')),
                                confirmations: txData.confirmations,
                                fileId: txData.fileId,
                                desc: txData.desc
                            }
                            if ($scope.userId === txData.userId) transaction.isCreator = true
                            $scope.transactions.push(transaction)
                        })
                    })
                    .catch(function (err) {
                        console.error(err)
                        UtilsFactory.Toast({
                            text: $translate.instant('general.error.' + err.status),
                            type: 'error'
                        })
                    })
            };

            function init() {
                $scope.keywords = {}
                $scope.selected = [];
                $scope.token = StorageFactory.get('token')
                $scope.userId = StorageFactory.get('userId')
                $scope.query = {
                    order: 'date',
                    limit: 3,
                    page: 1
                };
                $scope.transactions = []
                $scope.getTransactions()
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

            $scope.search = function () {
                /*if (!$scope.keywords.fromDate) {
                 return $scope.getTransactions()
                 }*/
                if (_.isEmpty($scope.keywords)) {
                    return $scope.getTransactions()
                }
                if ($scope.keywords.fromDate) $scope.keywords.fromTimestamp = $scope.keywords.fromDateFull.unix
                if ($scope.keywords.toDate) $scope.keywords.toTimestamp = $scope.keywords.toDateFull.unix
                if ($scope.keywords.fromDate && $scope.keywords.toDate) {
                    if ($scope.keywords.fromTimestamp > $scope.keywords.toTimestamp) {
                        return UtilsFactory.Toast({
                            text: $translate.instant('transaction.list.400'),
                            type: $scope.msg.type
                        })
                    }
                }
                var searchParams = {
                    token: StorageFactory.get('token'),
                    txId: ($scope.keywords.txId == undefined ? '' : $scope.keywords.txId),
                    desc: ($scope.keywords.desc == undefined ? '' : $scope.keywords.desc)
                }
                if ($scope.keywords.fromTimestamp) searchParams.fromDate = $scope.keywords.fromTimestamp
                if ($scope.keywords.toTimestamp) searchParams.toDate = $scope.keywords.toTimestamp
                UtilsFactory.HttpPost('/searchTransaction', searchParams)
                    .then(function (payload) {
                        var transaction = {}
                        $scope.transactions = []
                        _.forEach(payload.data, function (txData, txId) {
                            transaction = {
                                txId: txData.txId,
                                created: moment.unix(txData.txCreatedDate / 1000).format($translate.instant('dateformat')),
                                confirmations: txData.confirmations,
                                fileId: txData.fileId,
                                desc: txData.desc
                            }
                            if ($scope.userId === txData.userId) transaction.isCreator = true
                            $scope.transactions.push(transaction)
                        })
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

            init();

        }]);
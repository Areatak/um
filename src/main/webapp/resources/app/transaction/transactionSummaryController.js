angular.module('myApp')
    .controller('TransactionSummaryController', ['$scope', 'data', '$mdDialog', 'UtilsFactory', 'StorageFactory','$translate',
        function ($scope, data, $mdDialog, UtilsFactory, StorageFactory,$translate) {
            init(data)

            function init(data) {
                if (data.dataComplete) {
                    var now = moment(new Date());
                    var created = moment(new Date(data.txCreatedDate));
                    var diff = moment.duration(now.diff(created));
                    var totalMinutesAgo = parseInt(diff.asMinutes());
                    $scope.trx = {
                        hash: data.dataHash,
                        created: moment.unix(data.txCreatedDate / 1000).format($translate.instant('dateformat')),
                        fileId: data.fileId,
                        confirmations: data.confirmations,
                        txId: data.txId,
                        id: data.id,
                        desc: data.desc,
                        /*duration: {
                         days: parseInt(diff.days()),
                         hours: parseInt(diff.hours()),
                         minutes: parseInt(diff.minutes()),
                         seconds: parseInt(diff.seconds())
                         },*/
                        minutesAgo: totalMinutesAgo,
                        minutesToConfirmFrom: 10 - totalMinutesAgo,
                        minutesToConfirmTo: 20 - totalMinutesAgo
                    }
                } else {
                    var url = 'findTnxById';
                    var params = {id: data.id}
                    /*if (data.isTrxMine) {
                        url = 'findUserTnxByTxId'
                        params.token = StorageFactory.get('token')
                    } else {
                        url = 'findTnxByTxId'
                    }*/
                    return getTransaction(url, params)
                }
            }

            $scope.cancel = function () {
                $mdDialog.hide('cancel');
            };

            function getTransaction(url, params) {
                UtilsFactory.HttpPost(url, params)
                    .then(function (payload) {
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
                            duration: {
                                days: parseInt(diff.days()),
                                hours: parseInt(diff.hours()),
                                minutes: parseInt(diff.minutes()),
                                seconds: parseInt(diff.seconds())
                            },
                            minutesAgo: totalMinutesAgo,
                            minutesToConfirmFrom: 10 - totalMinutesAgo,
                            minutesToConfirmTo: 20 - totalMinutesAgo
                        }
                    })
                    .catch(function (err) {

                    })
            }
        }]);